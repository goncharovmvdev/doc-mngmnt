package doc.mngmnt.firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import doc.mngmnt.firestore.properties.FirebaseConfigurationProperties;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
@EnableConfigurationProperties({FirebaseConfigurationProperties.class})
public class FirebaseConfiguration {
    @Autowired
    private FirebaseConfigurationProperties properties;

    @Bean
    @SneakyThrows
    public FirebaseOptions firebaseOptions() {
        // TODO: 27.04.2021 бахнуть токен сюда
        FileInputStream fileInputStream = new FileInputStream(properties.getTokenFilePath());

        return FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(fileInputStream))
            // TODO: 27.04.2021 че еще за урл
            .setDatabaseUrl(properties.getDbUrl())
            .build();
    }

    @Bean
    public Firestore firestore() {
        FirebaseApp.initializeApp(firebaseOptions());

        return FirestoreClient.getFirestore();
    }
}
