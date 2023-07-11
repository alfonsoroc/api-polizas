package com.polizasservice.polizasservice.dto;

public class WebResponseDTO {
    private short type;

    private String message;

    private Object data;

    public WebResponseDTO(){}

    public WebResponseDTO(short type, String message, Object data) {
        this.type = type;
        this.message = message;
        this.data = data;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
