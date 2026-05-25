package com.SpringNotificationHub.NotificationServ.service;

import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;
import jakarta.mail.MessagingException;
import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.SpringNotificationHub.NotificationServ.model.ChannelType;
import nl.martijndwars.webpush.Notification;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.util.concurrent.Flow.Subscription;

@Getter
@Setter
@Service
public class PushService implements BroadcastChannel{


    @Override
     public String send(NotificationEntity notificationEntity)  throws MessagingException {

        return  notificationEntity.getMessage();
    }

    public boolean pushMessage(NotificationEntity notification, String recipientToken){
        try{
            Message message = Message.builder()
            .setToken(recipientToken)
            .setNotification(Notification.builder()
                .setTitle(notification.getTitle())
                .setBody(notification.getMessage())
                .build())
            .build();
            
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Mensagem enviado com sucesso " + response);

            return true;
        } catch (FirebaseMessagingException e){
            e.printStackTrace();
            return false;
        }
    }

     @Override
    public ChannelType type(){
        return ChannelType.PUSH;
    }
}
