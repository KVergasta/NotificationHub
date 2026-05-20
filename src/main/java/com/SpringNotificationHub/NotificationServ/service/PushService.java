package com.SpringNotificationHub.NotificationServ.service;

import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;
import jakarta.mail.MessagingException;
import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.SpringNotificationHub.NotificationServ.model.ChannelType;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import java.util.concurrent.Flow.Subscription;

@Getter
@Setter
@Service
public class PushService implements BroadcastChannel{
    private String numero;
    private PushService pushService;

    public PushService(String string, String string2, String string3){

        Security.addProvider(new BouncyCastleProvider());
        
        this.pushService = new PushService(
            "BHBE8HHXk5Yhs-qI0opfFu-Zi2UdDT3KL7vGXrOtX0GnkwUlBzfpTKVTDHjiHb3-VWNOXzwEMnTPCbZpGmfLleI",
            "malH9rJgHiqTBnRoHUhXpoVxn1SgzrmcKGW6m8YxVmQ",
            "mailto:notificationhubservice@gmail.com"
        );
    }

    @Override
     public String send(NotificationEntity message)  throws MessagingException {

        return  message.getMessage();
    }

     @Override
    public ChannelType type(){
        return ChannelType.PUSH;
    }
}
