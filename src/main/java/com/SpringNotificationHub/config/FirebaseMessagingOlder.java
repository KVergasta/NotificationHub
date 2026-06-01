package com.SpringNotificationHub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseMessagingOlder {

    @Value("${app.firebase.config-path}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource;

        // Se o caminho começar com /etc/secrets, indica o ambiente de produção na nuvem
        if (firebaseConfigPath.startsWith("/etc/secrets")) {
            resource = resourceLoader.getResource("file:" + firebaseConfigPath);
        } else {
            // Caso contrário, carrega a partir do caminho local configurado
            resource = resourceLoader.getResource("file:" + firebaseConfigPath);
            
            // Alternativa de segurança caso o arquivo local não use o prefixo de arquivo direto
            if (!resource.exists()) {
                resource = resourceLoader.getResource("classpath:firebase-service-account.json");
            }
        }

        if (!resource.exists()) {
            throw new IOException("ERRO CRÍTICO: O arquivo de credenciais do Firebase não foi localizado no caminho: " + firebaseConfigPath);
        }

        try (InputStream serviceAccount = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        }

        return FirebaseMessaging.getInstance();
    }
}