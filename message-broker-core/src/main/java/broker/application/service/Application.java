package broker.application.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"broker.configuration.jwt", "broker.configuration.websecurity",
        "broker.application", "broker.datasource", "broker.messages", "broker.subjects", "broker.users"}   )
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
