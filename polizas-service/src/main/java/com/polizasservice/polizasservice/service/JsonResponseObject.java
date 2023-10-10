package com.polizasservice.polizasservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polizasservice.polizasservice.dto.Data;
import com.polizasservice.polizasservice.dto.PolizasDTO;

import java.util.ArrayList;
import java.util.List;

public class JsonResponseObject {
    public ObjectNode respuestaJsonObject(List<PolizasDTO> resultadoConsultaPolizas)  {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode responseArray = objectMapper.createArrayNode();
        ObjectNode responseObj = objectMapper.createObjectNode();

        List<Data> dataList = new ArrayList<>();

        for (PolizasDTO polizasDTO : resultadoConsultaPolizas) {
            Data dataObj = new Data();
            ObjectNode meta = objectMapper.createObjectNode();
            Poliza poliza = new Poliza();
            Empleado empleado = new Empleado();
            DetalleArticulo detalleArticulo = new DetalleArticulo();



            meta.put("status", "ok");
            responseObj.set("meta", meta);

            poliza.setIDPoliza(polizasDTO.getIdpoliza());
            poliza.setCantidad(polizasDTO.getCantidad());

            dataObj.setPoliza(poliza);


            empleado.setNombre(polizasDTO.getNombre());
            empleado.setApellido(polizasDTO.getApellido());
            dataObj.setEmpleado(empleado);



            detalleArticulo.setSku(polizasDTO.getSku());
            detalleArticulo.setNombre(polizasDTO.getArticulo());

            dataObj.setDetalleArticulo(detalleArticulo);


            dataList.add(dataObj);



        }
        JsonNode articuloJson = objectMapper.valueToTree(dataList);

        responseObj.set("data", articuloJson);

        responseArray.add(responseObj);



        return responseObj;
    }
}
