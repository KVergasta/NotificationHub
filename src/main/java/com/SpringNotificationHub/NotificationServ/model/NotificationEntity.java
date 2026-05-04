package com.SpringNotificationHub.NotificationServ.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 
    // definir o status atraves do apache kafka, para verificar se a mensagem foi enviada ou não
    // definir o status como "PENDING" quando a notificação for criada, e depois atualizar para "SENT" ou "FAILED" dependendo do resultado do envio
    // criar uma tabela no frontend para exibir as notificações e seus status, permitindo que os usuários vejam quais notificações foram enviadas com sucesso e quais falharam
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

}