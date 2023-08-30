package com.polizasservice.polizasservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polizasservice.polizasservice.configuracion.Loggs;
import com.polizasservice.polizasservice.configuracion.FiltraRespuesta;
import com.polizasservice.polizasservice.dao.PolizasDAO;
import com.polizasservice.polizasservice.dto.PolizasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PolizaService {
    @Autowired
    protected JdbcTemplate jdbcTemplateService;

    @Autowired
    protected InventarioService inventarioService;

    @Autowired
    protected PolizasDAO polizasDAO;

    Loggs loggs = new Loggs();

    JsonResponseObject jsonResponseObjesct = new JsonResponseObject();
    StatusMensaje statusMensaje = new StatusMensaje();

    int opcion = 0;

    String mensaje = "";

    public ObjectNode consultarPolizas(int poliza, int empleado) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseService = objectMapper.createObjectNode();

        try {
            List<PolizasDTO> polizasService = polizasDAO.consultarPolizas(poliza, empleado);
            if (polizasService.size() > 0) {
                responseService = jsonResponseObjesct.RespuestaJsonObject(polizasService);

            } else {
                mensaje = "Error al consultar poliza con empleado:"+empleado;
                responseService = statusMensaje.RetornoMensajeStatus(mensaje, opcion);
            }
            return responseService;
        } catch (Exception ex) {
            mensaje = "Ha ocurrido un error al consultar la poliza";
            responseService = statusMensaje.RetornoMensajeStatus(mensaje, opcion);
            return responseService;

        }


    }

    public ObjectNode GuardarPoliza(float cantidad, int empleado, int sku) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseObj = objectMapper.createObjectNode();

        ObjectNode consultaInventario = inventarioService.consultarInventario(sku);

        float cantidadService =(float) consultaInventario.get("data").get(0).get("cantidad").asDouble();
        if(cantidadService < cantidad){

          return statusMensaje.RetornoMensajeStatus("Cantidad excede existencia de sku en inventario",opcion);
        }else {
            try {
                List<PolizasDTO> listaPoliza = polizasDAO.GuardarPoliza(cantidad, empleado, sku);
                if (listaPoliza.size() > 0) {
                    responseObj = jsonResponseObjesct.RespuestaJsonObject(listaPoliza);
                } else {

                }

            } catch (Exception ex) {
                mensaje = "Ha ocurrido un error en los grabados de la poliza";
                responseObj = statusMensaje.RetornoMensajeStatus(mensaje, opcion);

                return responseObj;
            }
            return responseObj;
        }
    }

    public ObjectNode ActaliazrPoliza(int poliza, float cantidad, int sku) {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseObj = objectMapper.createObjectNode();
        Integer folio;
        ObjectNode consultaInventario = inventarioService.consultarInventario(sku);

        float cantidadService =(float) consultaInventario.get("data").get(0).get("cantidad").asDouble();
        if(cantidadService < cantidad){

            return statusMensaje.RetornoMensajeStatus("Cantidad excede existencia de sku en inventario",0);
        }else {

            try {
                folio = polizasDAO.ActaliazrPoliza(poliza, cantidad, sku);

                if (folio != null && folio == poliza) {
                    opcion = 1;
                    mensaje = "Se ha actualizado correctamente la poliza #:" + poliza;
                    responseObj = statusMensaje.RetornoMensajeStatus(mensaje, opcion);
                } else {
                    throw new Exception();
                }

            } catch (Exception ex) {

                mensaje = "Ha ocurrido un error al actualizar la poliza #: " + poliza;
                responseObj = statusMensaje.RetornoMensajeStatus(mensaje, 0);

            }
            return responseObj;
        }
    }

    public ObjectNode eliminarPoliza(int poliza) {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseObj = objectMapper.createObjectNode();

        Integer folio;
        try {
            folio = polizasDAO.eliminarPoliza(poliza);

            if (folio != null && folio == poliza) {
                opcion = 1;
                mensaje = "Se ha eliminado correctamenta la poliza #:"+folio;
                responseObj = statusMensaje.RetornoMensajeStatus(mensaje, opcion);
            }

        } catch (Exception ex) {
            mensaje = "Ha ocurrido un error al eliminar la poliza" + poliza;
            responseObj = statusMensaje.RetornoMensajeStatus(mensaje, opcion);

        }
        return responseObj;
    }


}
