package com.polizasservice.polizasservice.configuracion;

import com.polizasservice.polizasservice.dao.PolizasDAO;
import com.polizasservice.polizasservice.dto.PolizasDTO;
import com.polizasservice.polizasservice.service.PolizaService;
import org.springframework.context.annotation.Bean;

public class ConfiguracionPolizas {
@Bean
public PolizasDAO consultarPoliza(){return new PolizaService();}

@Bean
public PolizasDAO GuardarPoliza(){return new PolizaService();}

@Bean
public PolizasDAO ActaliazrPoliza(){return new PolizaService();}

@Bean
public PolizasDAO eliminarPoliza(){return new PolizaService();}

}
