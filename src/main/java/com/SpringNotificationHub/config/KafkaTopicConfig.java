package com.SpringNotificationHub.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    
    @Bean
    public NewTopic topicOrderProcessed(){
        return new NewTopic("notification-order-processed", 2, (short) 1);
    }
}
