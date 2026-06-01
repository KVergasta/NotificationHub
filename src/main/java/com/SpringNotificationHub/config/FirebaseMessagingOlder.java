package com.SpringNotificationHub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class firebaseMessagingOlder {

    @Value("${app.firebase.config-path}")
    private String configPath;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {        
    InputStream serviceAccount;
        
        // Se o caminho começar com /etc/secrets (ambiente da Render), lê como arquivo do sistema
        if (configPath.startsWith("/etc/secrets") || configPath.contains("/") || configPath.contains("\\")) {
            serviceAccount = new FileInputStream(configPath);
        } else {
            // Caso contrário, tenta ler do classpath local
            serviceAccount = getClass().getClassLoader().getResourceAsStream(configPath);
        }

        if (serviceAccount == null) {
            throw new IllegalArgumentException("O arquivo 'Service Account' não foi encontrado no caminho: " + configPath);
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        return FirebaseMessaging.getInstance();
    }
}