package com.SpringNotificationHub.NotificationServ.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringNotificationHub.NotificationServ.exceptions.NotFoundException;
import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.SpringNotificationHub.NotificationServ.model.TypeEntity;
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
    
// Adicione o 'broadcasts' aqui para o Spring preencher a lista
public GeneratorNotification(TypeRepository typeRepository, 
                               NotificationRepository notificationRepository, 
                               List<BroadcastChannel> broadcasts) {
    this.typeRepository = typeRepository;
    this.notificationRepository = notificationRepository;
    this.broadcasts = broadcasts; 
}

    // public void sendMail(NotificationEntity msg, String info){

    //     emailService.send(msg, info);
    // }

    public String generatorMsg (NotificationEntity notification){
        return broadcasts.stream()
            .filter(channel -> channel.type() == notification.getType())
            .findFirst()
            .map(channel -> {
                channel.send(notification);
                // sendMail(notification, "kauvergasta12@gmail.com");
                return "Sucess"; // Ou o retorno que seu canal der
            })
            .orElseThrow(() -> new RuntimeException("No channel found for type: " + notification.getType()));
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