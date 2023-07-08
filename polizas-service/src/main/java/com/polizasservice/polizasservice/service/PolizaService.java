package com.polizasservice.polizasservice.service;

import com.polizasservice.polizasservice.configuracion.Loggs;
import com.polizasservice.polizasservice.configuracion.FiltraRespuesta;
import com.polizasservice.polizasservice.dao.PolizasDAO;
import com.polizasservice.polizasservice.dto.PolizasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class PolizaService extends PolizasDAO {
    @Autowired
    protected JdbcTemplate jdbcTemplateService;

    Loggs loggs = new Loggs();
    ResponseEntity<String> respuesta = ResponseEntity.ok().build();
    JsonResponse jsonResponse = new JsonResponse();
    StatusMensaje statusMensaje = new StatusMensaje();

    List<PolizasDTO> listPolizasDto = new ArrayList<>();
    List<PolizasDTO> listaLimpia = new ArrayList<>();

    int opcion = 0;

    String mensaje = "";
    @Override
    public ResponseEntity<String> consultarPolizas(int poliza, int empleado )  {
        String fallo = String.valueOf(empleado);
        try{
        loggs.loggsDebug("SE EJECUTA LA FUNCION: fun_buscarPoliza");
        String sql = "select IDPoliza,cantidad,nombre,apellido,SKU,articulo from fun_buscarPoliza(?,?)";
            listPolizasDto = jdbcTemplateService.query(sql,new Object[]{poliza,empleado}, new BeanPropertyRowMapper<>(PolizasDTO.class));

            if(listPolizasDto.isEmpty()){
                mensaje   = "No se encontro la poliza #"+poliza;
                respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
            }else{
            for(PolizasDTO polizasDTO : listPolizasDto){
                PolizasDTO polizaLimpia = new PolizasDTO();
                polizaLimpia.setIDPoliza(polizasDTO.getIDPoliza());
                polizaLimpia.setCantidad(polizasDTO.getCantidad());
                polizaLimpia.setNombre(FiltraRespuesta.LimpiarCode(polizasDTO.getNombre()));
                polizaLimpia.setApellido(FiltraRespuesta.LimpiarCode(polizasDTO.getApellido()));
                polizaLimpia.setSKU(polizasDTO.getSKU());
                polizaLimpia.setArticulo(FiltraRespuesta.LimpiarCode(polizasDTO.getArticulo()));
                listaLimpia.add(polizaLimpia);

            }

            respuesta = jsonResponse.RespuestaJson(listaLimpia);
            listaLimpia.clear();
            return respuesta;
            }

            return respuesta;
        }

        catch (Exception ex){
             mensaje = "Ha ocurrido un error al consultar la poliza";
            respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
            loggs.loggsError("ERROR AL EJECUTAR LA FUNCION fun_buscarPoliza: "+ex);
            return respuesta;
        }

    }
    @Override
    public ResponseEntity<String> GuardarPoliza (float cantidad, String fecha, int empleado, int sku)  {

        String formatoFecha = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(formatoFecha);


        try {
            Date fechaConsulta = dateFormat.parse(fecha);
            loggs.loggsDebug("SE EJECUTA LA FUNCION fun_crearPoliza");
            String sql = "Select IDPoliza,cantidad,nombre,apellido,SKU,articulo from fun_crearPoliza(?,?,?,?) ";
            listPolizasDto = jdbcTemplateService.query(sql, new Object[]{cantidad, fechaConsulta, empleado, sku}, new BeanPropertyRowMapper<>(PolizasDTO.class));


            for (PolizasDTO polizasDTO : listPolizasDto) {
                PolizasDTO polizaLimpia = new PolizasDTO();
                polizaLimpia.setIDPoliza(polizasDTO.getIDPoliza());
                polizaLimpia.setCantidad(polizasDTO.getCantidad());
                polizaLimpia.setNombre(FiltraRespuesta.LimpiarCode(polizasDTO.getNombre()));
                polizaLimpia.setApellido(FiltraRespuesta.LimpiarCode(polizasDTO.getApellido()));
                polizaLimpia.setSKU(polizasDTO.getSKU());
                polizaLimpia.setArticulo(FiltraRespuesta.LimpiarCode(polizasDTO.getArticulo()));
                listaLimpia.add(polizaLimpia);

                respuesta = jsonResponse.RespuestaJson(listaLimpia);
            }
            listaLimpia.clear();
            return respuesta;
        }catch (Exception ex){
            loggs.loggsError("ERROR AL EJECUTAR LA FUNCION fun_crearPoliza: "+ex);
            mensaje = "Ha ocurrido un error en los grabados de la poliza";

          respuesta =  statusMensaje.RetornoMensajeStatus(mensaje,opcion);

          return respuesta;
        }

    }
    @Override
    public ResponseEntity<String> ActaliazrPoliza (int poliza,float cantidad,int empleado,int sku){

        String sql = "SELECT fun_actualizarPoliza(?,?,?,?)";
        Integer folio;

        try {
            loggs.loggsDebug("SE EJECUTA LA FUNCION: fun_actualizarPoliza");
            folio = jdbcTemplateService.queryForObject(sql,new Object[]{poliza,cantidad,empleado,sku},Integer.class);

            if(folio != null && folio == poliza){
                opcion = 1;
                mensaje =  "Se ha actualizado correctamente la poliza #: "+poliza;

                respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
            }

        }catch (Exception ex){
            mensaje = "Ha ocurrido un error al actualizar la poliza #: "+sku;
            respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
            loggs.loggsError("ERROR AL EJECUTAR LA FUNCION fun_actualizarPoliza: "+ex);
        }
        return respuesta;
    }
    @Override
    public ResponseEntity<String> eliminarPoliza(int poliza) {

        String sql = "SELECT fun_eliminarPoliza(?)";
        Integer folio;
        try {

            loggs.loggsDebug("SE EJECUTA LA FUNCION: fun_eliminarPoliza");
            folio = jdbcTemplateService.queryForObject(sql,new Object[]{poliza},Integer.class);

            if(folio != null && folio == poliza) {
                    opcion = 1;
                    mensaje = "Se ha eliminado correctamenta la poliza #:" + folio;
                    respuesta = statusMensaje.RetornoMensajeStatus(mensaje, opcion);
            }

        }catch (Exception ex){
            mensaje = "Ha ocurrido un error al eliminar la poliza"+poliza;
            respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
            loggs.loggsError("ERROR AL EJECUTAR LA FUNCION fun_eliminarPoliza: "+ex);

        }
        return respuesta;
    }






}
