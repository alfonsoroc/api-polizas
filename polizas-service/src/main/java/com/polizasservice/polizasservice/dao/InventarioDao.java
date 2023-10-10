package com.polizasservice.polizasservice.dao;

import com.polizasservice.polizasservice.configuracion.FiltraRespuesta;
import com.polizasservice.polizasservice.configuracion.Loggs;
import com.polizasservice.polizasservice.dto.InventarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class InventarioDao {

    @Autowired
    protected JdbcTemplate jdbcTemplateService;
    Loggs loggs = new Loggs();

    public List<InventarioDTO> consultaInventario(Integer sku){

        List<InventarioDTO> cleanListInventario = new ArrayList<>();

        String query = "select codigo,nombre,cantidad,familia,precio from fun_buscarSku(?)";
        try{
            loggs.loggsDebug("Se ejecuta fun_buscarSku");
            List<InventarioDTO> listInventarioDto = jdbcTemplateService.query(query,new Object[]{sku},new BeanPropertyRowMapper<>(InventarioDTO.class));
            for(InventarioDTO inventarioDTO: listInventarioDto){
                InventarioDTO cleanInventario = new InventarioDTO();
                cleanInventario.setCodigo(inventarioDTO.getCodigo());
                cleanInventario.setNombre(FiltraRespuesta.limpiarCode(inventarioDTO.getNombre()));
                cleanInventario.setFamilia(FiltraRespuesta.limpiarCode(inventarioDTO.getFamilia()));
                cleanInventario.setCantidad(inventarioDTO.getCantidad());
                cleanInventario.setPrecio(inventarioDTO.getPrecio());
                cleanListInventario.add(cleanInventario);

            }



        }catch (Exception ex){
            loggs.loggsDebug("Erro al consultal fun_buscarSku"+ex);
            ex.getMessage();
        }
        return cleanListInventario;
    }

}
