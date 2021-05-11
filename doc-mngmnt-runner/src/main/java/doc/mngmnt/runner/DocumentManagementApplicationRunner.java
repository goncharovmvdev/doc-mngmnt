package doc.mngmnt.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// TODO: 01.05.2021 jpa тесты в отдельном модуле
@SpringBootApplication(scanBasePackages = {
    "doc.mngmnt.rest",
    "doc.mngmnt.security",
    "doc.mngmnt.service",
    "doc.mngmnt.service.api",
    "doc.mngmnt.service.impl",
})
@EntityScan(basePackages = {"doc.mngmnt.entity"})
@EnableJpaRepositories(basePackages = {"doc.mngmnt.repository"})
public class DocumentManagementApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(DocumentManagementApplicationRunner.class, args);
    }
}