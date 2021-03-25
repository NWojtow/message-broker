package messagebroker.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"messagebroker.messagebrokerclient.newmessages",
        "messagebroker.messagebrokerclient.oldmessages"})
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}

