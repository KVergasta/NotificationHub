package com.SpringNotificationHub.NotificationServ.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import lombok.Getter;
import lombok.Setter;
import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.SpringNotificationHub.NotificationServ.model.ChannelType;

@Getter
@Setter
@Service
public class EmailService implements BroadcastChannel {
    @Value("${app.mail.gmail.username}") 
    private String admMailAddress;

    private final JavaMailSender gmailSender;
    private final JavaMailSender outlookSender;
    private final TemplateEngine templateEngine;

    public EmailService(
        @Qualifier("gmailSender") JavaMailSender gmailSender, 
        @Qualifier("outlookSender") JavaMailSender outlookSender,
        TemplateEngine templateEngine
    ){
        this.gmailSender = gmailSender;
        this.outlookSender = outlookSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public String send(NotificationEntity notificationEntit) throws MessagingException {

            JavaMailSender chosenSender = notificationEntit.getInfoUser().contains("@gmail.com") ? gmailSender : outlookSender;

            MimeMessage mimeMessage = chosenSender.createMimeMessage();
            Context context = new Context();
            context.setVariable("username", notificationEntit.getInfoUser()); 
            context.setVariable("messageContent", notificationEntit.getMessage());
            context.setVariable("title", notificationEntit.getTitle());
            
            String process = templateEngine.process("notification.html", context);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
            );
            helper.setTo(notificationEntit.getInfoUser());
            helper.setFrom(chosenSender == gmailSender ? admMailAddress : "notif.hub@outlook.com");
            helper.setSubject(notificationEntit.getTitle()+" - API Notification Hub");
            helper.setText(process,true);

            chosenSender.send(mimeMessage);
            
            return  "The message is send";

    }

    @Override
    public ChannelType type(){
        return ChannelType.EMAIL;
    }


}