package com.polizasservice.polizasservice.configuracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polizasservice.polizasservice.dto.AppMessages;
import com.polizasservice.polizasservice.dto.Meta;
import com.polizasservice.polizasservice.dto.TypeResponse;
import com.polizasservice.polizasservice.dto.WebResponseDTO;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
    protected AppConfig appConfig;

    private String cachedToken = "";

    private String validaToken;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Loggs loggs = new Loggs();
        HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        final ObjectMapper objectMapper = new ObjectMapper();

        boolean filterRedirection = false;

        String token = req.getHeader(HttpHeaders.AUTHORIZATION);
        LifeToken lifeToken = new LifeToken();

        try {
            if (lifeToken.validaVidaToken(token)) {
                if (appConfig.isIgnoreSession()) {
                    filterRedirection = validateSSO(req, res, objectMapper);
                }
                if (filterRedirection) {
                    chain.doFilter(request, response);
                }
            } else {
                throw new IOException();
            }
        } catch (IOException | NumberFormatException | ServletException e) {
            loggs.loggsError(e.getMessage());
            if (lifeToken.validaVidaToken(token)) {
                setResponseHeaders(res, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                res.getWriter().write(new ObjectMapper().writeValueAsString(
                        new WebResponseDTO(TypeResponse.TYPE_RESTICTIVE, AppMessages.MESSAGE_MESA_AYUDA, null)));
            } else {
                setResponseHeaders(res, HttpServletResponse.SC_NOT_ACCEPTABLE);
                res.getWriter().write(new ObjectMapper().writeValueAsString(
                        new WebResponseDTO(TypeResponse.TYPE_OUT, AppMessages.OUT_MESSAGE, null)));

            }
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

    private boolean validateSSO(HttpServletRequest req, HttpServletResponse res, ObjectMapper objectMapper) throws IOException {
        boolean filterRedirection;
        if (req.getHeader(HttpHeaders.AUTHORIZATION) == null && Integer.parseInt(appConfig.getIgnoreHeadersEmpty()) == 0) {
            setResponseHeaders(res, HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write(objectMapper.writeValueAsString(
                    new WebResponseDTO(TypeResponse.TYPE_UNAUTHORISED, AppMessages.UNAUTHORIZED_MESSAGE,
                            new Meta(AppMessages.JWT_ERROR, 401, AppMessages.UNAUTHORISED_MESSAGE))));
            filterRedirection = false;
        } else {
            if (req.getHeader(HttpHeaders.AUTHORIZATION) != null) {
                validaToken = req.getHeader(HttpHeaders.AUTHORIZATION);

                filterRedirection = callVerify(req, res, objectMapper);

            } else {
                filterRedirection = false;
            }
        }

        return filterRedirection;
    }

    public boolean callVerify(HttpServletRequest req, HttpServletResponse res, ObjectMapper objectMapper) throws IOException {
        boolean valido;
        LifeToken lifeToken = new LifeToken();

        try {
            if (!cachedToken.equals(validaToken)) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                SecureString auth = new SecureString(req.getHeader(HttpHeaders.AUTHORIZATION));

                headers.set(HttpHeaders.AUTHORIZATION, auth.asString());

                ResponseEntity<String> authResponse = RestUtil.post(appConfig.getAuthUriNpos(), "", headers);

                if (authResponse.getStatusCode() == HttpStatus.OK) {
                    String tokenObtener = authResponse.getBody();
                    cachedToken = lifeToken.obtenerToken(tokenObtener);
                    validaToken = "";
                    valido = true;
                } else {
                    setResponseHeaders(res, HttpServletResponse.SC_UNAUTHORIZED);
                    res.getWriter().write(objectMapper.writeValueAsString(
                            new WebResponseDTO(TypeResponse.TYPE_UNAUTHORISED, AppMessages.UNAUTHORISED_MESSAGE,
                                    new Meta(AppMessages.JWT_ERROR, 401, AppMessages.UNAUTHORISED_MESSAGE))));
                    validaToken = "";
                    valido = false;
                }
            } else {
                valido = true;
            }
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().value() == 401) {
                setResponseHeaders(res, HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write(objectMapper.writeValueAsString(
                        new WebResponseDTO(TypeResponse.TYPE_UNAUTHORISED, AppMessages.UNAUTHORISED_MESSAGE,
                                new Meta(AppMessages.JWT_ERROR, 401, AppMessages.UNAUTHORISED_MESSAGE))));
                return false;
            } else {
                throw e;
            }

        }
        return valido;

    }

}
