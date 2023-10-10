package com.polizasservice.polizasservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polizasservice.polizasservice.configuracion.Loggs;
import com.polizasservice.polizasservice.configuracion.MiException;
import com.polizasservice.polizasservice.dao.PolizasDAO;
import com.polizasservice.polizasservice.dto.PolizasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
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

    public PolizaService() {
    }


    public ObjectNode consultarPolizas(int poliza, int empleado) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseService = objectMapper.createObjectNode();

        try {
            List<PolizasDTO> polizasService = polizasDAO.consultarPolizas(poliza, empleado);
            if (!polizasService.isEmpty()) {
                responseService = jsonResponseObjesct.respuestaJsonObject(polizasService);

            } else {
                responseService = statusMensaje.retornoMensajeStatus("Error al consultar poliza con empleado:"+empleado, opcion);
            }
            return responseService;
        } catch (Exception ex) {
            responseService = statusMensaje.retornoMensajeStatus("Ha ocurrido un error al consultar la poliza", opcion);
            return responseService;

        }


    }

    public ObjectNode guardarPoliza(float cantidad, int empleado, int sku) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseObj = objectMapper.createObjectNode();

        ObjectNode consultaInventario = inventarioService.consultarInventario(sku);

        float cantidadService =(float) consultaInventario.get("data").get(0).get("cantidad").asDouble();
        if(cantidadService < cantidad){

          return statusMensaje.retornoMensajeStatus("Cantidad excede existencia de sku en inventario",opcion);
        }else {
            try {
                List<PolizasDTO> listaPoliza = polizasDAO.GuardarPoliza(cantidad, empleado, sku);
                if (!listaPoliza.isEmpty()) {
                    responseObj = jsonResponseObjesct.respuestaJsonObject(listaPoliza);
                }
            } catch (Exception ex){
                responseObj = statusMensaje.retornoMensajeStatus("Ha ocurrido un error en los grabados de la poliza", opcion);

                return responseObj;
            }
            return responseObj;
        }
    }

    public ObjectNode actualiazarPoliza(int poliza, float cantidad, int sku) {
        ObjectNode responseObj;
        ObjectNode consultaInventario = inventarioService.consultarInventario(sku);

        float cantidadService =(float) consultaInventario.get("data").get(0).get("cantidad").asDouble();
        if(cantidadService < cantidad){

            return statusMensaje.retornoMensajeStatus("Cantidad excede existencia de sku en inventario",0);
        }else {

            try {
             Integer folio = polizasDAO.actualizarPoliza(poliza, cantidad, sku);
                if (folio != null && folio == poliza) {
                    opcion = 1;
                    responseObj = statusMensaje.retornoMensajeStatus("Se ha actualizado correctamente la poliza #:" + poliza, opcion);
                } else {
                    throw new MiException("Error en actualizar Poliza");
                }

            } catch (Exception ex) {
                responseObj = statusMensaje.retornoMensajeStatus("Ha ocurrido un error al actualizar la poliza #: " + poliza, 0);

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
                responseObj = statusMensaje.retornoMensajeStatus("Se ha eliminado correctamenta la poliza #:"+folio, opcion);
            }

        } catch (Exception ex) {
            responseObj = statusMensaje.retornoMensajeStatus("Ha ocurrido un error al eliminar la poliza" + poliza, opcion);

        }
        return responseObj;
    }


}
