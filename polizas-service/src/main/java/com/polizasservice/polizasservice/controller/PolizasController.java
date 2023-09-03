package com.polizasservice.polizasservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polizasservice.polizasservice.dao.PolizasDAO;
import com.polizasservice.polizasservice.service.InventarioService;
import com.polizasservice.polizasservice.service.PolizaService;
import com.polizasservice.polizasservice.service.StatusMensaje;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polizas")
public class PolizasController {
    @Autowired
    protected PolizasDAO polizasDao;

    @Autowired
    protected InventarioService inventarioService;

    @Autowired
    PolizaService polizaService;


    @GetMapping("/buscar")
    public ObjectNode buscarPolizas(
            @RequestParam int folio,
            @RequestParam int empleado
    )  {
        StatusMensaje statusMensaje = new StatusMensaje();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseObj = objectMapper.createObjectNode();
        try {
            return polizaService.consultarPolizas (folio,empleado);
        }
        catch (Exception ex){
            int opcion = 0;
            String mensaje = "Ha ocurrido un error al consltar la poliza";
            responseObj = statusMensaje.retornoMensajeStatus(mensaje,opcion);
            return responseObj;
        }

    }

    @GetMapping("/Guardar")
    public ObjectNode guardarPolizas(
            @RequestParam float cantidad,
            @RequestParam int empleado,
            @RequestParam int sku
            ) {
        return polizaService.guardarPoliza(cantidad,empleado,sku);
    }

    @GetMapping("/Actualizar")
    public ObjectNode actualizar(
            @RequestParam int poliza,
            @RequestParam float cantidad,
            @RequestParam int sku
    ){
        return polizaService.actualiazarPoliza(poliza,cantidad,sku);
    }

    @GetMapping("/eliminar")
    public ObjectNode eliminar(
            @RequestParam int poliza
    ){
        return polizaService.eliminarPoliza(poliza);
    }

    @GetMapping("/inventario")
    public ObjectNode consultarInventario(@RequestParam int sku){

        return inventarioService.consultarInventario(sku);
    }
}
