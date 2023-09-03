package com.polizasservice.polizasservice.dao;

import com.polizasservice.polizasservice.configuracion.FiltraRespuesta;
import com.polizasservice.polizasservice.configuracion.Loggs;
import com.polizasservice.polizasservice.dto.PolizasDTO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class PolizasDAO {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    Loggs loggs = new Loggs();

    public List<PolizasDTO> consultarPolizas(int ipoliza, int iempleado) {
        List<PolizasDTO> listaLimpia = new ArrayList<>();
        String sql = "select IDPoliza,cantidad,nombre,apellido,SKU,articulo from fun_buscarPoliza(?,?)";

        try {
            loggs.loggsDebug("SE EJECUTA LA FUNCION: fun_buscarPoliza");
            List<PolizasDTO> resultadoConsultaPolizas = jdbcTemplate.query(sql, new Object[]{ipoliza, iempleado}, new BeanPropertyRowMapper<>(PolizasDTO.class));


            for (PolizasDTO polizasDTO : resultadoConsultaPolizas) {
                PolizasDTO polizaLimpia = new PolizasDTO();
                polizaLimpia.setIdpoliza(polizasDTO.getIdpoliza());
                polizaLimpia.setCantidad(polizasDTO.getCantidad());
                polizaLimpia.setNombre(FiltraRespuesta.limpiarCode(polizasDTO.getNombre()));
                polizaLimpia.setApellido(FiltraRespuesta.limpiarCode(polizasDTO.getApellido()));
                polizaLimpia.setSku(polizasDTO.getSku());
                polizaLimpia.setArticulo(FiltraRespuesta.limpiarCode(polizasDTO.getArticulo()));
                listaLimpia.add(polizaLimpia);

            }

        } catch (Exception ex) {
            loggs.loggsDebug("Error al ejecutar Poliza" + ex);
        }
        return listaLimpia;
    }


    public List<PolizasDTO> GuardarPoliza(float cantidad, int empleado, int sku) {

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaService = fechaActual.format(formateador);
        String formatoFecha = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(formatoFecha);


        List<PolizasDTO> listPolizasDto = new ArrayList<>();
        List<PolizasDTO> listaLimpiaPolizas = new ArrayList<>();

        String sql = "Select IDPoliza,cantidad,nombre,apellido,SKU,articulo from fun_crearPoliza(?,?,?,?)";
        try {
            loggs.loggsDebug("Se ejecuta fun_crearPoliza");
            Date fechaConsulta = dateFormat.parse(fechaService);
            listPolizasDto = jdbcTemplate.query(sql, new Object[]{cantidad, fechaConsulta, empleado, sku}, new BeanPropertyRowMapper<>(PolizasDTO.class));
            for (PolizasDTO polizasDTO : listPolizasDto) {
                PolizasDTO polizaLimpia = new PolizasDTO();
                polizaLimpia.setIdpoliza(polizasDTO.getIdpoliza());
                polizaLimpia.setCantidad(polizasDTO.getCantidad());
                polizaLimpia.setNombre(FiltraRespuesta.limpiarCode(polizasDTO.getNombre()));
                polizaLimpia.setApellido(FiltraRespuesta.limpiarCode(polizasDTO.getApellido()));
                polizaLimpia.setSku(polizasDTO.getSku());
                polizaLimpia.setArticulo(FiltraRespuesta.limpiarCode(polizasDTO.getArticulo()));
                listaLimpiaPolizas.add(polizaLimpia);
            }
        } catch (Exception ex) {
            loggs.loggsDebug("Error al consultar la funcion fun_crearPoliza" + ex);
        }
        return listaLimpiaPolizas;
    }

    public Integer actualizarPoliza(int poliza, float cantidad, int sku) {

        String sql = "SELECT fun_actualizarPoliza(?,?,?)";
        Integer folio = 0;

        try {
            loggs.loggsDebug("Se ejecuta fun_actualizarPoliza");
            folio = jdbcTemplate.queryForObject(sql, new Object[]{poliza, cantidad, sku}, Integer.class);

        } catch (Exception ex) {
          loggs.loggsDebug("Error al ejecutar funcion fun_actualizarPoliza"+ex);
        }
        return folio;
    }

    public Integer eliminarPoliza(int poliza) {

        String sql = "SELECT fun_eliminarPoliza(?)";
        Integer folio = 0;

        try {
            loggs.loggsDebug("Se ejecuta fun_eliminarPoliza");
            folio = jdbcTemplate.queryForObject(sql, new Object[]{poliza}, Integer.class);
        } catch (Exception ex) {
           loggs.loggsDebug("Error al ejecutar funcion fun_eliminarPoliza(?)"+ex);
        }
        return folio;

    }


}
