package com.polizasservice.polizasservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.polizasservice.polizasservice.dto.InventarioDTO;


import java.util.ArrayList;
import java.util.List;

public class JsonResponseInventario {
    public ObjectNode respuestaJsonInventario(List<InventarioDTO> resultadoConsultaInventario) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode responseArray = objectMapper.createArrayNode();
        ObjectNode responseObj = objectMapper.createObjectNode();
        List<InventarioDTO> dataList = new ArrayList<>();

        for (InventarioDTO inventarioDTO : resultadoConsultaInventario) {

            ObjectNode meta = objectMapper.createObjectNode();

            meta.put("status", "ok");
            responseObj.set("meta", meta);

            dataList.add(inventarioDTO);

        }
        JsonNode articuloJson = objectMapper.valueToTree(dataList);

        responseObj.set("data", articuloJson);

        responseArray.add(responseObj);

        return responseObj;
    }
}
