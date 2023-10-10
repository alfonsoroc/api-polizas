package com.polizasservice.polizasservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@SpringJUnitConfig(TestConfig.class)
class PolizasControllerTest {
    @Autowired
    private PolizasController polizasController;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    AuthenticationController authenticationController = new AuthenticationController();
    String token = authenticationController.authenticate();

    PolizasControllerTest() throws IOException {
    }

    @Test
    void buscarPolizas() throws Exception {


        int folio = 0;
        int empleado = 100;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/polizas/buscar")
                        .param("folio", String.valueOf(folio))
                        .param("empleado", String.valueOf(empleado))
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    void guardarPolizas() throws Exception {

        int empleado = 100;
        float cantidad = 20;
        int sku = 321321;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/polizas/Guardar")
                        .param("cantidad", String.valueOf(cantidad))
                        .param("empleado", String.valueOf(empleado))
                        .param("sku", String.valueOf(sku))
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    void actualizar() throws Exception {
        int poliza = 100;
        int cantidad = 15;
        int sku = 321321;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/polizas/Actualizar")
                        .param("poliza", String.valueOf(poliza))
                        .param("cantidad", String.valueOf(cantidad))
                        .param("sku", String.valueOf(sku))
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();

        assertEquals(200, status);


    }

    @Test
    void eliminar() throws Exception {
        int poliza = 100;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/polizas/eliminar")
                        .param("poliza", String.valueOf(poliza))

                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();

        assertEquals(200, status);

    }
}