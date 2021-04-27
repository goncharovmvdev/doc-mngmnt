package doc.mngmnt.repository.config;

import doc.mngmnt.repository.config.provider.PersistOpsAuditorAware;
import doc.mngmnt.repository.config.provider.ZonedDateTimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class AuditConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new PersistOpsAuditorAware();
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return new ZonedDateTimeProvider();
    }
}
