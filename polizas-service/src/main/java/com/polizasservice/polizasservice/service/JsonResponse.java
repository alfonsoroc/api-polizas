package com.polizasservice.polizasservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polizasservice.polizasservice.dto.PolizasDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class JsonResponse {
    public ResponseEntity<String> RespuestaJson (List<PolizasDTO> resultadoConsultaPolizas) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode responseArray = objectMapper.createArrayNode();
        ResponseEntity<String> respuesta = ResponseEntity.ok().build();

            for (PolizasDTO polizasDTO : resultadoConsultaPolizas) {

                ObjectNode meta = objectMapper.createObjectNode();
                ObjectNode data = objectMapper.createObjectNode();
                Poliza poliza = new Poliza();
                Empleado empleado = new Empleado();
                DetalleArticulo detalleArticulo = new DetalleArticulo();
                ObjectNode responseObj = objectMapper.createObjectNode();

                meta.put("status", "ok");
                responseObj.set("meta", meta);

                poliza.setIDPoliza(polizasDTO.getIDPoliza());
                poliza.setCantidad(polizasDTO.getCantidad());
                JsonNode polizaJson = objectMapper.valueToTree(poliza);
                data.set("poliza", polizaJson);

                empleado.setNombre(polizasDTO.getNombre());
                empleado.setApellido(polizasDTO.getApellido());
                JsonNode empleadoJson = objectMapper.valueToTree(empleado);
                data.set("empleado", empleadoJson);

                detalleArticulo.setSKU(polizasDTO.getSKU());
                detalleArticulo.setNombre(polizasDTO.getArticulo());
                JsonNode articuloJson = objectMapper.valueToTree(detalleArticulo);
                data.set("detalleArticulo", articuloJson);
                responseObj.set("data", data);

                responseArray.add(responseObj);

            }

            String jsonString = responseArray.toString();
            respuesta = ResponseEntity.ok(jsonString);
            return respuesta;
    }

}
