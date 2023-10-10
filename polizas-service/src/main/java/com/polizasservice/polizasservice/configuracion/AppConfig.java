package com.polizasservice.polizasservice.configuracion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "app")
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {
    private String appId;
    private String appKey;


   @Value("${app.authUriNpos}")
    private String authUriNpos;
    @Value("${app.isIgnoreSession}")
    private boolean ignoreSession;

    public boolean isIgnoreSession() {
        return ignoreSession;
    }

    public void setIgnoreSession(boolean ignoreSession) {
        this.ignoreSession = ignoreSession;
    }

    public String getAuthUriNpos() {
        return authUriNpos;
    }

    public void setAuthUriNpos(String authUriNpos) {
        this.authUriNpos = authUriNpos;
    }

    private String ignoreHeadersEmpty;

    public String getIgnoreHeadersEmpty() {
        return ignoreHeadersEmpty;
    }

    public void setIgnoreHeadersEmpty(String ignoreHeadersEmpty) {
        this.ignoreHeadersEmpty = ignoreHeadersEmpty;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }



}
