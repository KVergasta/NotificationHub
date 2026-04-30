package com.SpringNotificationHub.NotificationServ.model;

import com.SpringNotificationHub.NotificationServ.exceptions.FieldNotAccetableException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum ChannelType {

    EMAIL("E"),
    WHATSAPP("W"),
    SMS("S"),
    PUSH("P");

    private final String categoria;

    ChannelType(String categoria){
        this.categoria = categoria;
    }

    @JsonValue
    public String getCategoria(){
        return this.categoria;
    }

    @JsonCreator
    public static ChannelType fromCode(String code){
        for (ChannelType channel : values()){
            if(channel.getCategoria().equals(code)){
                return channel;
            } 
        }
        throw new FieldNotAccetableException("Invalid channel type code: " + code);
    }

}