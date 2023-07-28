package com.polizasservice.polizasservice.configuracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polizasservice.polizasservice.dto.AppMessages;
import com.polizasservice.polizasservice.dto.Meta;
import com.polizasservice.polizasservice.dto.TypeResponse;
import com.polizasservice.polizasservice.dto.WebResponseDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



@Component
@Order(value = 1)
public class SessionFilter implements Filter {
  @Autowired
  private AppConfig appConfig;

  @Autowired
  private CacheController cacheController;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
    Loggs loggs = new Loggs();
    HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse res = (HttpServletResponse) response;
    final ObjectMapper objectMapper = new ObjectMapper();

    boolean filterRedirection = false;
    try{
    if(appConfig.isIgnoreSession()){
      filterRedirection = validateSSO(req,res,objectMapper);
    }
    if(filterRedirection) {
      chain.doFilter(request, response);
    }
    }catch (IOException | NumberFormatException | ServletException e) {
      loggs.loggsError(e.getMessage());
      setResponseHeaders(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      res.getWriter().write(new ObjectMapper().writeValueAsString(new WebResponseDTO(TypeResponse.TYPE_RESTICTIVE, AppMessages.MESSAGE_MESA_AYUDA, null)));
    } catch (RestClientException ex) {
      loggs.loggsError(AppMessages.JWT_ERROR.concat(": ").concat(ex.getMessage()));
      setResponseHeaders(res, HttpServletResponse.SC_UNAUTHORIZED);
      res.getWriter().write(objectMapper.writeValueAsString(
              new WebResponseDTO(TypeResponse.TYPE_UNAUTHORISED, AppMessages.UNAUTHORISED_MESSAGE,
                      new Meta(AppMessages.JWT_ERROR, 401, AppMessages.UNAUTHORISED_MESSAGE))));
    }



  }

  private void setResponseHeaders(HttpServletResponse res, int status) {
    res.reset();
    res.setStatus(status);
    res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    res.setCharacterEncoding(StandardCharsets.UTF_8.name());
  }

  private boolean validateSSO(HttpServletRequest req, HttpServletResponse res, ObjectMapper objectMapper) throws IOException{
    boolean filterRedirection = true;
    if(req.getHeader(HttpHeaders.AUTHORIZATION)==null && Integer.parseInt(appConfig.getIgnoreHeadersEmpty()) ==0){
      setResponseHeaders(res,HttpServletResponse.SC_UNAUTHORIZED);
      res.getWriter().write(objectMapper.writeValueAsString(
              new WebResponseDTO(TypeResponse.TYPE_UNAUTHORISED, AppMessages.UNAUTHORIZED_MESSAGE,
                      new Meta(AppMessages.JWT_ERROR,401,AppMessages.UNAUTHORISED_MESSAGE))));
      filterRedirection = false;
    } else {
       if (req.getHeader(HttpHeaders.AUTHORIZATION) != null) {
        String tokenValida = req.getHeader(HttpHeaders.AUTHORIZATION);
       String valida = cacheController.obtenerToken(tokenValida);
        filterRedirection = callVerify(req, res, objectMapper);
      } else {
        filterRedirection = true;
      }
    }

    return filterRedirection;
  }

  public boolean callVerify (HttpServletRequest req, HttpServletResponse res, ObjectMapper objectMapper) throws IOException {
    boolean valido;

    try {
      //Se incluye la cabecera y valida el contenido
      //verifica token
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      SecureString auth = new SecureString(req.getHeader(HttpHeaders.AUTHORIZATION));
      headers.set(HttpHeaders.AUTHORIZATION, auth.asString());

      ResponseEntity<String> authResponse = RestUtil.post(appConfig.getAuthUriNpos(), "", headers);
      //respuesta sin error
      if (authResponse.getStatusCode() == HttpStatus.OK) {
        String jsonString = authResponse.getBody();
        JSONObject json = new JSONObject(jsonString);
        String token = json.getJSONObject("data").getString("token");
        String valida = cacheController.guardarToken(token);
        valido = true;
        //token valido
      } else {
        //Token invalido
        setResponseHeaders(res, HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write(objectMapper.writeValueAsString(
                new WebResponseDTO(TypeResponse.TYPE_UNAUTHORISED, AppMessages.UNAUTHORISED_MESSAGE,
                        new Meta(AppMessages.JWT_ERROR, 401, AppMessages.UNAUTHORISED_MESSAGE))));
        valido = false;
      }
    }catch (RestClientResponseException e){
      if(e.getStatusCode().value() == 401){
        setResponseHeaders(res, HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write(objectMapper.writeValueAsString(
                new WebResponseDTO(TypeResponse.TYPE_UNAUTHORISED, AppMessages.UNAUTHORISED_MESSAGE,
                        new Meta(AppMessages.JWT_ERROR, 401, AppMessages.UNAUTHORISED_MESSAGE))));
        return  false;
      }else{
        throw e;
      }

    }
    return valido;

  }

}
