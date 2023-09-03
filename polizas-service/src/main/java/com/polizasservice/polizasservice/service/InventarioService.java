package com.polizasservice.polizasservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polizasservice.polizasservice.configuracion.MiException;
import com.polizasservice.polizasservice.dao.InventarioDao;
import com.polizasservice.polizasservice.dto.InventarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {
    private final InventarioDao inventarioDao;

    @Autowired
    public InventarioService(InventarioDao inventarioDao) {
        this.inventarioDao = inventarioDao;
    }

    public ObjectNode consultarInventario(Integer sku) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseService = objectMapper.createObjectNode();
        JsonResponseInventario jsonResponseObjesct = new JsonResponseInventario();
        StatusMensaje statusMensaje = new StatusMensaje();
        Integer opcion = 0;
        try {
            List<InventarioDTO> listInventario = inventarioDao.consultaInventario(sku);

            if (!listInventario.isEmpty()) {
                responseService = jsonResponseObjesct.respuestaJsonInventario(listInventario);
            } else {
                throw new MiException("Error");
            }

        } catch (Exception ex) {
            String mensaje = "Ha ocurrido un error al consultar Inventario";
            responseService = statusMensaje.retornoMensajeStatus(mensaje, opcion);
            return responseService;
        }

        return responseService;
    }

}
