package com.polizasservice.polizasservice.configuracion;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class Loggs {
    private static final Logger logger = LoggerFactory.getLogger(Loggs.class);

    public void loggsDebug(String mensaje){
        logger.info(mensaje);

    }
   public void loggsError(String mensaje){
        logger.error(mensaje);

   }
}
