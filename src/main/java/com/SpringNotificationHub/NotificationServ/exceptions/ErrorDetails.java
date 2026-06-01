package com.SpringNotificationHub.NotificationServ.exceptions;

import java.util.Date;

public class ErrorDetails {
    private String message;
    private Date timestamp;

    // Construtor Padrão
    public ErrorDetails() {}

    // Construtor Completo
    public ErrorDetails(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters e Setters Manuais
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}