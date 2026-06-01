package com.SpringNotificationHub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseMessagingOlder {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream serviceAccount = null;

        // 1. Tenta carregar o arquivo a partir do caminho seguro de produção na nuvem (Render)
        File productionFile = new File("/etc/secrets/notificationhub-d9fb5-firebase-adminsdk-fbsvc-95711e229d.json");
        
        if (productionFile.exists()) {
            serviceAccount = new FileInputStream(productionFile);
        } else {
            // 2. Se o arquivo da nuvem não existir, usa o caminho da sua máquina local para testes
            File localFile = new File("C:\\Users\\kauve\\Documents\\code\\projeto_notificationHub\\NotificationHubAPI\\src\\main\\resources\\notificationhub-d9fb5-firebase-adminsdk-fbsvc-95711e229d.json");
            
            if (localFile.exists()) {
                serviceAccount = new FileInputStream(localFile);
            } else {
                // Caso não encontre em nenhum dos dois lugares, lança um erro detalhado explicativo
                throw new IOException("ERRO CRÍTICO: Arquivo de credenciais do Firebase não foi encontrado localmente nem no servidor de produção!");
            }
        }

        // 3. Inicializa o Firebase usando o fluxo de dados selecionado acima
        try (InputStream input = serviceAccount) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(input))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            System.err.println("Falha ao inicializar o aplicativo Firebase Messaging: " + e.getMessage());
            throw e;
        }

        return FirebaseMessaging.getInstance();
    }
}