package com.SpringNotificationHub.NotificationServ.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private String title;
    private String message;
    private String userIp;

    // E adicione estes dois métodos no final do seu arquivo
    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }
    // Verifique se o seu atributo original se chama "type" ou "channelType"
    @Enumerated(EnumType.STRING)
    private ChannelType type; 
    
    @Enumerated(EnumType.STRING)
    private StatusType status;
    
    private String infoUser;

    // Construtor Padrão Obrigatorio
    public NotificationEntity() {}

    // Getters e Setters Completos
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public ChannelType getType() { return type; }
    public void setType(ChannelType type) { this.type = type; }

    public StatusType getStatus() { return status; }
    public void setStatus(StatusType status) { this.status = status; }

    public String getInfoUser() { return infoUser; }
    public void setInfoUser(String infoUser) { this.infoUser = infoUser; }
}