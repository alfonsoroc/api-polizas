package com.polizasservice.polizasservice.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polizasservice.polizasservice.configuracion.Loggs;
import com.polizasservice.polizasservice.dto.PolizasDTO;
import com.polizasservice.polizasservice.service.JsonResponse;

import com.polizasservice.polizasservice.service.StatusMensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.ObjectError;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PolizasDAO {
    @Autowired
    protected JdbcTemplate jdbcTemplate;


    public ResponseEntity<String> consultarPolizas(int Ipoliza, int Iempleado )  {
        StatusMensaje statusMensaje =  new StatusMensaje();
        String mensaje;
        int opcion = 0;
        ResponseEntity<String> respuesta = ResponseEntity.ok().build();
        try{
            String sql = "select IDPoliza,cantidad,nombre,apellido,SKU,articulo from fun_buscarPoliza(?,?)";
            List <PolizasDTO> resultadoConsultaPolizas = jdbcTemplate.query(sql,new Object[]{Ipoliza,Iempleado}, new BeanPropertyRowMapper<>(PolizasDTO.class));

            resultadoConsultaPolizas.clear();
            return respuesta;

        }
        catch (Exception ex){
            mensaje = "Ha ocurrido un error al consultar la poliza";
            respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
        }
        return respuesta;
    }


    public ResponseEntity<String> GuardarPoliza (float cantidad, String fecha,int empleado, int sku)  {
        String formatoFecha = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(formatoFecha);
        ResponseEntity<String> respuesta = ResponseEntity.ok().build();
        List<PolizasDTO> listPolizasDto = new ArrayList<>();
        String sql = "Select IDPoliza,cantidad,nombre,apellido,SKU,articulo from fun_crearPoliza()";
        StatusMensaje statusMensaje =  new StatusMensaje();
        String mensaje;
        int opcion = 0;
        try {
            Date fechaConsulta = dateFormat.parse(fecha);
            listPolizasDto = jdbcTemplate.query(sql, new Object[]{cantidad, fechaConsulta, empleado, sku}, new BeanPropertyRowMapper<>(PolizasDTO.class));
            listPolizasDto.clear();
            return respuesta;
        }catch (Exception ex){
            mensaje = "Ha ocurrido un error al eliminar la poliza";
            respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
        }
        return respuesta;
    }
    public ResponseEntity<String> ActaliazrPoliza (int poliza,float cantidad,int empleado,int sku) {
        StatusMensaje statusMensaje =  new StatusMensaje();
        ResponseEntity<String> respuesta = ResponseEntity.ok().build();
        String sql = "SELECT fun_actualizarPoliza(?,?,?,?)";
        Integer folio;
        int opcion = 0;
        try {
         folio = jdbcTemplate.queryForObject(sql,new Object[]{poliza,cantidad,empleado,sku},Integer.class);
         if(folio !=null && folio>0){
             opcion = 1;
             String mensaje =  "Se ha actualizado correctamente la poliza #: "+sku;

             respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
         }

        }catch (Exception ex){
            String mensaje = "Ha ocurrido un error al actualizar la poliza #: "+sku;
            respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
        }
        return respuesta;
    }

    public ResponseEntity<String> eliminarPoliza(int poliza) {
        StatusMensaje statusMensaje = new StatusMensaje();
        ResponseEntity<String> respuesta =  ResponseEntity.ok().build();
        String sql = "SELECT fun_eliminarPoliza(?)";
        Integer folio;
        int opcion=0;
        String mensaje;
        try {

          folio = jdbcTemplate.queryForObject(sql,new Object[]{poliza},Integer.class);
          if(folio != null && folio == poliza) {
              opcion = 1;
              mensaje = "Se ha eliminado correctamenta la poliza #:" + folio;
              respuesta = statusMensaje.RetornoMensajeStatus(mensaje, opcion);
          }
        }catch (Exception ex){
            mensaje = "Ha ocurrido un error al eliminar la poliza"+poliza;
            respuesta = statusMensaje.RetornoMensajeStatus(mensaje,opcion);
        }
        return respuesta;


        }







}
