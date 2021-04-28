package doc.mngmnt.service.impl.notification.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "mail.properties")
@ConstructorBinding
@PropertySource(value = "mail.properties")
@Validated
@RequiredArgsConstructor
@Getter
public class MailProperties {
    @NotBlank
    private final String from;
}
