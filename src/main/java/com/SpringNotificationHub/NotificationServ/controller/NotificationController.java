package com.SpringNotificationHub.NotificationServ.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import com.SpringNotificationHub.NotificationServ.exceptions.NotFoundException;
import com.SpringNotificationHub.NotificationServ.model.BroadcastChannel;
import com.SpringNotificationHub.NotificationServ.model.NotificationEntity;
import com.SpringNotificationHub.NotificationServ.model.TypeEntity;
import com.SpringNotificationHub.NotificationServ.service.GeneratorNotification;



@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private GeneratorNotification generatorNotification;
    @Autowired
    private List<BroadcastChannel> broadcastChannel;

@PostMapping("/generator")
    public ResponseEntity<?> notification (
        @RequestBody NotificationEntity notificationEntity
    ){
            BroadcastChannel bc = broadcastChannel.stream()
            .filter(channel -> channel.type() == notificationEntity.getType())
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Channel not found for type: " + notificationEntity.getType()));

            String type = bc.type().name();

            generatorNotification.saveNotification(notificationEntity);

            return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .header("Channel", type)
            .header("title", notificationEntity.getTitle())
            .body(generatorNotification.generatorMsg(notificationEntity));
        }

@GetMapping("/listChannels")
        public ResponseEntity<List<TypeEntity>> getAllTypes(){
            return ResponseEntity
            .status(HttpStatus.OK)
            .body(generatorNotification.getAllTypes());
        }

@GetMapping("/listNotifications")
        public ResponseEntity<List<NotificationEntity>> getAllNotifications(){
            return ResponseEntity
            .status(HttpStatus.OK)
            .body(generatorNotification.getAllNotifications());
        }

@GetMapping("/{id}")
        public ResponseEntity<NotificationEntity> findNotification(@PathVariable("id") UUID id){
            return ResponseEntity
            .status(HttpStatus.OK)
            .body(generatorNotification.findNotification(id)
            .orElseThrow(() -> new NotFoundException("Notification not found with id: " + id)));
        }
        
@PostMapping("/create")
        public ResponseEntity<NotificationEntity> createNotification(@RequestBody NotificationEntity notificationEntity){
            return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(generatorNotification.saveNotification(notificationEntity));
        }

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteNotification(@PathVariable("id") UUID id) {
        generatorNotification.deleteNotification(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Id "+ id +" It's deleted");
        }
}





