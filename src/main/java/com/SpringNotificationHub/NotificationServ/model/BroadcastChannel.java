package com.SpringNotificationHub.NotificationServ.model;
import jakarta.mail.MessagingException;

public interface BroadcastChannel {
        public String send(NotificationEntity notificationEntity)  throws MessagingException;
        public ChannelType type();
}