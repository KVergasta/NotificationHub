package com.SpringNotificationHub.config;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FirebaseMessagingConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("notificationhub-d9fb5-firebase-adminsdk-fbsvc-5f1e13eed5.json");

        if (serviceAccount == null) {
            throw new IllegalArgumentException("O arquivo 'Service Account' não foi encontrado ");
        }

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);

        return FirebaseMessaging.getInstance(app);
    }
}
