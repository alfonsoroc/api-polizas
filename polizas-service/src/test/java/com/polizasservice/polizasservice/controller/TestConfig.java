package com.polizasservice.polizasservice.controller;

import com.polizasservice.polizasservice.dao.InventarioDao;
import com.polizasservice.polizasservice.dao.PolizasDAO;
import com.polizasservice.polizasservice.service.InventarioService;
import com.polizasservice.polizasservice.service.PolizaService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class TestConfig {

    @Bean
    public MockMvc mockMvc(WebApplicationContext webApplicationContext){
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Bean
    public PolizaService polizaService(){
        return new PolizaService();
    }

    @Bean
    public InventarioService inventarioService(){
        return new InventarioService();
    }
    @Bean
    public InventarioDao inventarioDao(){
        return new InventarioDao();
    }
    @Bean
    public PolizasDAO polizasDAO(){
        return new PolizasDAO();
    }
    @Bean
    public PolizasController polizasController(){
        return new PolizasController();
    }
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("username");
        dataSource.setPassword("password");
        return dataSource;
    }

}
