package com.polizasservice.polizasservice.configuracion;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "cache")
public class CacheController {
    @Cacheable(key = "#token",sync = true)
    public String guardarToken(String token){
        return token;
    }

    @Cacheable(key = "#token", sync = true)
    public String obtenerToken(String token) {
        return null;
    }
}


