package doc.mngmnt.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"doc.mngmnt"})
@EntityScan(basePackages = {"doc.mngmnt.entity"})
@EnableJpaRepositories(basePackages = {"doc.mngmnt.repository"})
public class DocumentManagementApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(DocumentManagementApplicationRunner.class, args);
    }
}
