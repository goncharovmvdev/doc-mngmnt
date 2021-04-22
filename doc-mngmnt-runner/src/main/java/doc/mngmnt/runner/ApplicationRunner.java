package doc.mngmnt.runner;

import doc.mngmnt.security.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = {SecurityConfig.class})
@EntityScan(basePackages = {"doc.mngmnt.entity"})
@EnableJpaRepositories(basePackages = {"doc.mngmnt.repository"})
@ComponentScan(basePackages = {"doc.mngmnt"})
public class ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}
