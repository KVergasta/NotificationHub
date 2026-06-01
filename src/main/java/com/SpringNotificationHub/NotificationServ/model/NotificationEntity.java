package com.SpringNotificationHub.NotificationServ.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.UUID;

@Entity
@Table(name = "notifications") // ou o nome da sua tabela
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private String title;
    private String message;
    private String type; // ou o tipo do seu Enum StatusType (ex: StatusType type)

    // Construtor Padrão (Obrigatório para o JPA)
    public NotificationEntity() {}

    // Getters e Setters Manuais
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Se o seu método retornar um Enum, ajuste o tipo de retorno aqui
    public String getType() { 
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}