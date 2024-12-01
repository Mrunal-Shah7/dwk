package com.xenostar.dwk;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Value("${FIREBASE_SERVICE_ACCOUNT_JSON}")
    private String firebaseServiceAccountJson;

    @PostConstruct
    public void initialize() throws IOException {
        if (firebaseServiceAccountJson == null || firebaseServiceAccountJson.isEmpty()) {
            throw new IllegalStateException("Firebase service account JSON is not configured. " +
                    "Please set the FIREBASE_SERVICE_ACCOUNT_JSON environment variable.");
        }

        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(
                firebaseServiceAccountJson.getBytes(StandardCharsets.UTF_8)
        );

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}