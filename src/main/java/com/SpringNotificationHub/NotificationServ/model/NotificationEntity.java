package com.SpringNotificationHub.NotificationServ.model;

import java.io.Serializable;
import java.util.UUID;

import ch.qos.logback.core.Context;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class NotificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    // criação do id
    private UUID id;
    
    // criação das colunas
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "type", nullable = false)
    private ChannelType type;
    
    @Column(name = "message", nullable = false)
    private String message;
    
    @Column(name = "user_account", nullable = false)
    private String infoUser;

}