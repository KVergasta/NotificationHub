package com.SpringNotificationHub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() {
        try {
            // Captura o texto do JSON direto da variável de ambiente da Render
            String firebaseJson = System.getenv("FIREBASE_CONFIG_JSON");

            if (firebaseJson == null || firebaseJson.isEmpty()) {
                System.out.println("⚠️ Variável FIREBASE_CONFIG_JSON não foi encontrada no ambiente.");
                return;
            }

            // Configura as opções de conexão com o Firebase usando o texto lido da memória
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(firebaseJson.getBytes())))
                    .build();

            // Evita que o Spring tente inicializar o Firebase duplicado caso a aplicação reinicie
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase inicializado com sucesso na nuvem!");
            }
            
        } catch (IOException e) {
            System.err.println("❌ Erro ao tentar ler as credenciais do Firebase: " + e.getMessage());
        }
    }
}