package doc.mngmnt.service.impl.google.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Set;

@Configuration
@PropertySource("classpath:google-drive.properties")
public class GoogleDriveConfig {
    @Value("${server.port}")
    private Integer port;
    @Value("${google.application.name}")
    private String applicationName;
    @Value("${google.credentials.file.path}")
    private String credentialsFilePath;
    @Value("${google.drive.scopes}")
    private Set<String> scopes;

    @Bean
    public JsonFactory jsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    public NetHttpTransport httpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    @Autowired
    public Drive getInstance(NetHttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        return new Drive.Builder(
            httpTransport,
            jsonFactory,
            getCredentials(
                httpTransport,
                jsonFactory
            )
        )
            .setApplicationName(applicationName)
            .build();
    }

    @Bean
    @Autowired
    public Credential getCredentials(NetHttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(credentialsFilePath);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            httpTransport,
            jsonFactory,
            clientSecrets,
            scopes
        )
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(credentialsFilePath)))
            .setAccessType("offline")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
            .setHost("127.0.0.1")
            .setPort(port)
            .build();
        return new AuthorizationCodeInstalledApp(flow, receiver)
            .authorize("doc-mngmnt");
    }
}
