package com.polizasservice.polizasservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;


public class StatusMensaje {
  public ObjectNode retornoMensajeStatus(String mensaje, int opcion){
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode responseObj = objectMapper.createObjectNode();
      ObjectNode meta = objectMapper.createObjectNode();
      ObjectNode data = objectMapper.createObjectNode();
      switch (opcion){
          case 0:
              meta.put("status", "FAILURE");
              responseObj.set("meta", meta);
              data.put("Mensaje:", mensaje);
              responseObj.set("data",data);
          break;
          case 1:
              meta.put("status","ok");
              responseObj.set("meta",meta);
              data.put("Mensaje:",mensaje);
              responseObj.set("data", data);
          break;

          default:


      }
        return responseObj;



  }
}
