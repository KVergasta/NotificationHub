package com.SpringNotificationHub.NotificationServ.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringNotificationHub.NotificationServ.exceptions.NotFoundException;
import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.SpringNotificationHub.NotificationServ.model.TypeEntity;
import com.SpringNotificationHub.NotificationServ.service.StreamService;
import com.SpringNotificationHub.NotificationServ.repository.NotificationRepository;
import com.SpringNotificationHub.NotificationServ.repository.TypeRepository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class GeneratorNotification {
    
    private TypeRepository typeRepository;
    private NotificationRepository notificationRepository;
    private List<BroadcastChannel> broadcasts;

    @Autowired
    @Lazy
    StreamService streamService;
    
public GeneratorNotification(TypeRepository typeRepository, 
                            NotificationRepository notificationRepository, 
                            List<BroadcastChannel> broadcasts) {
    this.typeRepository = typeRepository;
    this.notificationRepository = notificationRepository;
    this.broadcasts = broadcasts;
}


    public String generatorMsg (NotificationEntity notification, String ip){
        notification.setUserIp(ip);
        return broadcasts.stream()
            .filter(channel -> channel.type() == notification.getType())
            .findFirst()
            .map(channel -> {
                streamService.sendMessage(notification);
                return "Message status: " + notification.getStatus().toString();
            })
            .orElseThrow(() -> new RuntimeException("No channel found for type: " + notification.getType()));
    }

    public List<NotificationEntity> getMyTodayNotifications(String currentIp) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
        return notificationRepository.findTodayNotificationsByIp(currentIp, start, end);
    }

    public NotificationEntity saveNotification(NotificationEntity notificationEntity){
        return notificationRepository.save(notificationEntity);
    }

    public Optional<NotificationEntity> findNotification(UUID id){
        return notificationRepository.findById(id);
    }

    public List<NotificationEntity> getAllNotifications(){
        return notificationRepository.findAll();
    }

    @Transactional
    public void deleteNotification(UUID id){
        if (!notificationRepository.existsById(id)) {
        throw new NotFoundException("ID " + id + " não existe no banco.");
    }
        notificationRepository.deleteById(id);
        notificationRepository.flush();
    }

    public List<TypeEntity> getAllTypes(){
        return typeRepository.findAll();
    }

}