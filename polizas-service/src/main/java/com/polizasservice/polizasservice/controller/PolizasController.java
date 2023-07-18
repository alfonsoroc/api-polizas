package com.polizasservice.polizasservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polizasservice.polizasservice.dao.PolizasDAO;
import com.polizasservice.polizasservice.service.StatusMensaje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polizas")
public class PolizasController {
    @Autowired
    protected PolizasDAO polizasDao;


    @GetMapping("/buscar")
    public ObjectNode BuscarPolizas(
            @RequestParam int folio,
            @RequestParam int empleado
    )  {
        StatusMensaje statusMensaje = new StatusMensaje();
        ResponseEntity<String> respuesta = ResponseEntity.ok().build();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseObj = objectMapper.createObjectNode();
        try {
            return polizasDao.consultarPolizas(folio,empleado);
        }
        catch (Exception ex){
            int opcion = 0;
            String mensaje = "Ha ocurrido un error al consltar la poliza";
            respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
            return responseObj;
        }

    }

    @GetMapping("/Guardar")
    public ResponseEntity<String> GuardarPolizas(
            @RequestParam float cantidad,
            @RequestParam String fecha,
            @RequestParam int empleado,
            @RequestParam int sku
            ) {
        return polizasDao.GuardarPoliza(cantidad,fecha,empleado,sku);
    }

    @GetMapping("/Actualizar")
    public ResponseEntity<String> Actualizar(
            @RequestParam int poliza,
            @RequestParam float cantidad,
            @RequestParam int empleado,
            @RequestParam int sku
    ){
        return polizasDao.ActaliazrPoliza(poliza,cantidad,empleado,sku);
    }

    @GetMapping("/eliminar")
    public ResponseEntity<String> eliminar(
            @RequestParam int poliza
    ){
        return polizasDao.eliminarPoliza(poliza);
    }
}
