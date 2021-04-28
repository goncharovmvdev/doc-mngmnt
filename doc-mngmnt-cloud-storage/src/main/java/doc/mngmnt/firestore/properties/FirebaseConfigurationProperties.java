package doc.mngmnt.firestore.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

// TODO: 27.04.2021 пропертисорс в @ConfigurationProperties?
@ConfigurationProperties(prefix = "firebase.properties")
@ConstructorBinding
@PropertySource(value = "firebase.properties")
@Validated
@RequiredArgsConstructor
@Getter
public class FirebaseConfigurationProperties {
    @NotBlank
    private final String tokenFilePath;
    @NotBlank
    private final String dbUrl;
}
