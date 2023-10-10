package com.polizasservice.polizasservice.controller;

public class AuthenticationRequest {
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
