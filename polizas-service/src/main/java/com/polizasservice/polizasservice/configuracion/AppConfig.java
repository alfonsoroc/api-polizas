package com.polizasservice.polizasservice.configuracion;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@ConfigurationProperties(prefix = "app")
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {
    private String appId;
    private String appKey;

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
