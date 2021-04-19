package doc.mgmnt.service.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Set;

// TODO: 19.04.2021 что делать с @EnableConfigurationProperties? - мне не нравится подход с компонентом
@Component
@ConfigurationProperties(prefix = "service.user")
@ConstructorBinding
@Validated
@Data
public class UserServiceProperties {
    @NotNull
    private final Boolean accountNonExpired;
    @NotNull
    private final Boolean accountNonLocked;
    @NotNull
    private final Boolean credentialsNonExpired;
    @NotNull
    private final Boolean enabled;
    private final Set<String> defaultRoleNames;
}
