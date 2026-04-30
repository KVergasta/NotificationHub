package com.SpringNotificationHub.config;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.SpringNotificationHub.NotificationServ.model.ChannelType;
import com.SpringNotificationHub.NotificationServ.model.TypeEntity;
import com.SpringNotificationHub.NotificationServ.repository.TypeRepository;

@Configuration
public class DbInitializer {

    @Bean
    CommandLineRunner initDatabase(TypeRepository typeRepository){
        return args->{
            if (typeRepository.count() == 0) {
                TypeEntity email = new TypeEntity(ChannelType.EMAIL.getCategoria());
                TypeEntity push = new TypeEntity(ChannelType.PUSH.getCategoria());
                typeRepository.saveAll(List.of(email, push));
            }
        };
    }
}
