package com.polizasservice.polizasservice.dto;

public class Error {
    private String errorCode;
    private String userMessage;

    public Error(String code, String message){
        this.errorCode = code;
        this.userMessage = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

}
