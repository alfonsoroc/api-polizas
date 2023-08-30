package com.polizasservice.polizasservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;

public class StatusMensaje {
  public ObjectNode RetornoMensajeStatus(String mensaje, int opcion){
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode responseObj = objectMapper.createObjectNode();
      ObjectNode meta = objectMapper.createObjectNode();
      ObjectNode data = objectMapper.createObjectNode();
      ArrayNode responseArray = objectMapper.createArrayNode();
      ResponseEntity<String> respuesta = ResponseEntity.ok().build();

      switch (opcion){
          case 0:
              meta.put("status", "FAILURE");
              responseObj.set("meta", meta);

              data.put("Mensaje:", mensaje);
              responseObj.set("data",data);
              responseArray.add(responseObj);
              String jsonError = responseArray.toString();
              respuesta = ResponseEntity.ok(jsonError);
          break;
          case 1:
              meta.put("status","ok");
              responseObj.set("meta",meta);

              data.put("Mensaje:",mensaje);
              responseObj.set("data", data);

              responseArray.add(responseObj);
              String jsonCorrecto = responseArray.toString();
              respuesta = ResponseEntity.ok(jsonCorrecto);
          break;

          default:


      }
        return responseObj;



  }
}
