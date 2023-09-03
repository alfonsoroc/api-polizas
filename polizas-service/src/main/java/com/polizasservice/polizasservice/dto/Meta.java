package com.polizasservice.polizasservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {
    private String status;
    private Integer count;
    private Error error;
    private String transactionId;
    private Integer statusCode;
    private String timestamp;
    private String devMessage;
    private String message;
    private String time;
    private String timeElapsed;

    public Meta(String status, Integer statusCode, String message) {
        this.status = status;
        this.timestamp = LocalDateTime.now().toString();
        this.error = new Error(statusCode + "", message);

    }

    public Meta(String transactionId, String status, Integer statusCode) {
        this.transactionId = transactionId;
        this.status = status;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now().toString();
    }

    public Meta(String transactionId, String status, Integer statusCode, String message) {
        this.transactionId = transactionId;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public Meta(String transactionID, String time, String timeElapsed) {
        this.transactionId = transactionID;
        this.time = time;
        this.timeElapsed = timeElapsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(String timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

}
