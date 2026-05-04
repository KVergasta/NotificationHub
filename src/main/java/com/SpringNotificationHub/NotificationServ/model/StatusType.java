package com.SpringNotificationHub.NotificationServ.model;

import com.SpringNotificationHub.NotificationServ.exceptions.FieldNotAccetableException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum StatusType {
    
    PENDING("PENDING"),
    SENT("SENT"),
    FAILED("FAILED");
    
    private final String code;
    
    StatusType(String code){
        this.code = code;
    }
    
    @JsonValue
    public String getCode(){
        return this.code;
    }
    
    @JsonCreator
    public static StatusType fromCode(String code){
        for (StatusType status : values()){
            if(String.valueOf(status.getCode()).equals(code)){
                return status;
            } 
        }
        throw new FieldNotAccetableException("Invalid status type code: " + code);
    }

}