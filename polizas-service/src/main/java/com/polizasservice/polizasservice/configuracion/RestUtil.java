package com.polizasservice.polizasservice.configuracion;



import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestUtil {

    private RestUtil() {
    }

    private static ObjectMapper mapper;

    public static final String TRACE_METHOD = "TRACE";
    public static final String FAIL_STATUS = "FAIL";
    public static final String SUCCESS_STATUS = "SUCCESS";
    public static final String JWT_ERROR = "JWT_ERROR";
    public static final String CIPHER_ERROR = "CIPHER_ERROR";
    public static final String UNAUTHORIZED_MESSAGE = "Usted no está autorizado para acceder este recurso.";
    public static final String INVALID_TOKEN_MESSAGE = "El token de acceso no es valido.";
    public static final String INVALID_MESSAGE = "Mensaje invalido";
    public static final String INVALID_REQUEST = "Petición invalida";

    public static final short TYPE_OK = 0;
    public static final short TYPE_INFORMATIVE = 1;
    public static final short TYPE_CONDITIONAL = 2;
    public static final short TYPE_ERROR = 3;
    public static final short TYPE_RESTICTIVE = 5;

    public static final String APP_ID = "appId";
    public static final String APP_KEY = "appKey";
    public static final String DEVICE_ID = "deviceId";
    public static final String JWT = "JWT";
    public static final String EMPTY_JSON = "{}";

    private static HttpHeaders simpleHeaders;
    private static SecureString authorizationToken;

    static {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);

        simpleHeaders = new HttpHeaders();
    }
    private static final Logger logger = LoggerFactory.getLogger(RestUtil.class);

    public static ResponseEntity<String> post(String url, Object objPayload, HttpHeaders headers) throws JsonProcessingException, RestClientResponseException {
        return post(url, objPayload, null, headers);
    }

    public static ResponseEntity<String> post(String url, String jsonPayload, HttpHeaders headers) throws JsonProcessingException, RestClientResponseException {
        return post(url, null, jsonPayload, headers);
    }

    private static ResponseEntity<String> post(String url, Object objPayload, String jsonPayload, HttpHeaders headers) throws JsonProcessingException, RestClientResponseException {
        ResponseEntity<String> responseEntity = null;
        HttpEntity<String> requestEntity = null;
        UriComponentsBuilder uriBuilder = null;
        String payload = "";

        //si incluyen un objeto como request payload
        if (!ObjectUtils.isEmpty(objPayload)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            payload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objPayload);
        }

        //si incluyen una cadena json como request payload
        if (!ObjectUtils.isEmpty(jsonPayload)) {
            payload = jsonPayload;
        }

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            httpRequestFactory.setConnectionRequestTimeout(20 * 1000);
            httpRequestFactory.setConnectTimeout(20 * 1000);
            httpRequestFactory.setReadTimeout(20 * 1000);
            StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
            stringHttpMessageConverter.setWriteAcceptCharset(true);

            restTemplate = new RestTemplate(httpRequestFactory);
            restTemplate.getMessageConverters().add(0, stringHttpMessageConverter);

            uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
            requestEntity = new HttpEntity<>(payload, headers);
            setAuthorization(requestEntity.getHeaders().getFirst(AUTHORIZATION));

        } catch (Throwable e){
            throw e;
        }

        responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, requestEntity, String.class);

        return responseEntity;
    }

    private static void setHttpHeadersMap() {
        if (ObjectUtils.isEmpty(simpleHeaders)) {
            simpleHeaders = new HttpHeaders();
        }
    }

    public static HttpHeaders getSimpleHttpHeaders() {
        setHttpHeadersMap();
        simpleHeaders.setContentType(MediaType.APPLICATION_JSON);
        return simpleHeaders;
    }

    public static String getAuthorization() {
        return authorizationToken.asString();
    }

    private static String setAuthorization(String token) {
        authorizationToken = new SecureString(token);
        return authorizationToken.asString();
    }

    public static HttpHeaders getAuthorizationHttpHeaders(String token) {
        setHttpHeadersMap();
        simpleHeaders.set(HttpHeaders.AUTHORIZATION, token);
        simpleHeaders.setContentType(MediaType.APPLICATION_JSON);
        return simpleHeaders;
    }

}

