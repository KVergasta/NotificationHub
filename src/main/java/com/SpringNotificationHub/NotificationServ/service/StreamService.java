package com.SpringNotificationHub.NotificationServ.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.SpringNotificationHub.NotificationServ.model.ChannelType;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.SpringNotificationHub.NotificationServ.model.StatusType;
import com.SpringNotificationHub.NotificationServ.repository.NotificationRepository;

@Service
public class StreamService {
    
    private final KafkaTemplate<String, NotificationEntity> kafkaTemplate;

    private final Random random = new Random();

    @Autowired
    private EmailService emailService;

    @Autowired
    private PushService pushService;

    @Autowired
    private NotificationRepository notificationRepository;

    public StreamService(KafkaTemplate<String, NotificationEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @SuppressWarnings("null")
    public String sendMessage(NotificationEntity message) {
        int partition = random.nextInt(2); // Randomly select partition 0 or 1
        if (message.getType() == ChannelType.EMAIL) {
            kafkaTemplate.send("notification-stream-mail", partition, null, message);
        } else if (message.getType() == ChannelType.PUSH) {
            kafkaTemplate.send("notification-stream-push", partition, null, message);
        }
        return message.getStatus().toString();
    }

    // listen de email -- cada canal terá seu prórpio listen
@KafkaListener(topicPartitions = @TopicPartition(topic = "notification-stream-mail", partitions = {"0", "1"}))
public void listen(NotificationEntity message) {
    try {
        emailService.send(message);
        message.setStatus(StatusType.SENT);
        // notificationRepository.findById(message.getId()).ifPresent(d -> {
            // d.setStatus(StatusType.SENT);
            notificationRepository.save(message);
        // });
    } catch (Throwable e) {
        kafkaTemplate.send("notification-retry",message);
    }
}

@KafkaListener(topicPartitions = @TopicPartition(topic = "notification-stream-push", partitions = {"0", "1"}))
public void listenPush(NotificationEntity message) {
    try {
        pushService.send(message);
        notificationRepository.findById(message.getId()).ifPresent(d -> {
            d.setStatus(StatusType.SENT);
            notificationRepository.save(d);
        });
    } catch (Throwable e) {
        kafkaTemplate.send("notification-retry",message);
    }
}

@KafkaListener(topicPartitions = @TopicPartition(topic = "notification-retry", partitions = {"0", "1"}))
public void listenRetry(NotificationEntity message) {
    try {
        if(message.getInfoUser()==null || message.getInfoUser().isEmpty()){
            System.err.println("O campo infoUser está vazio");
        }
        if (message.getType() == ChannelType.EMAIL) {
            emailService.send(message);
        } else {
            pushService.send(message);
        } 
    } catch (Throwable e) {
        message.setStatus(StatusType.FAILED);
    } finally{
            this.saveMessage(message);
        
    }
}

    public void saveMessage(NotificationEntity notificationEntity){
        notificationRepository.save(notificationEntity);
    }

}
