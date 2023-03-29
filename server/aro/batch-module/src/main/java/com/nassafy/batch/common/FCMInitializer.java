package com.nassafy.batch.common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FCMInitializer {
    private static final Logger logger = LoggerFactory.getLogger(FCMInitializer.class);

    private FirebaseApp firebaseApp;

    // application yml 설정파일에 설정한 값 사용
    @Value("${fcm.key.path}")
    private String firebaseConfig;

    @Value("${fcm.projectID}")
    private String projectID;

    public FirebaseApp getFirebaseApp(){
        return this.firebaseApp;
    }
    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("firebase/" + firebaseConfig).getInputStream()))
                    .setProjectId(projectID)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
                logger.info("Firebase application has been initialized");
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            logger.error("Create FirebaseApp Error", e);
        }
    }
}
