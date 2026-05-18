package com.SpringNotificationHub.NotificationServ.service;

import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;
import jakarta.mail.MessagingException;
import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.SpringNotificationHub.NotificationServ.model.ChannelType;

@Getter
@Setter
@Service
public class PushService implements BroadcastChannel{
    private String numero;

    @Override
     public String send(NotificationEntity message)  throws MessagingException {
        return  message.getMessage();
    }

     @Override
    public ChannelType type(){
        return ChannelType.PUSH;
    }
}
