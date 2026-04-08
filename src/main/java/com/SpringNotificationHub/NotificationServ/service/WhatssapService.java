package com.SpringNotificationHub.NotificationServ.service;

import org.springframework.stereotype.Service;

import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.SpringNotificationHub.NotificationServ.model.ChannelType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class WhatssapService implements BroadcastChannel{

    @Override
    public String send(NotificationEntity message) {
        return  message.getMessage();
    }
    
    @Override
    public ChannelType type(){
        return ChannelType.WHATSAPP;
    }
}