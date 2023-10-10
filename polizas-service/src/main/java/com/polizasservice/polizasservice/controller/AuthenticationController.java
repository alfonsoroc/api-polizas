package com.polizasservice.polizasservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polizasservice.polizasservice.dto.TokenResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class AuthenticationController {
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate() throws IOException {
        AuthenticationRequest request  = new AuthenticationRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        request.setAppId("58ccba34-6382-481d-b87f-3fe7d95d430e");
        request.setAppKey("53d16da74313af15c29d5a486390a572e6255d4855fd7405d7b017a4de06bf76");
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(
                "https://apigateway.coppel.com:58443/sso-dev/api/v1/app/authenticate",
                request,
                String.class,
                headers
        );


        ObjectMapper mapper = new ObjectMapper();
        TokenResponseDTO tokenResponse = mapper.readValue(response, TokenResponseDTO.class);
        String token = tokenResponse.getData().getToken();
        return token;
    }
}
