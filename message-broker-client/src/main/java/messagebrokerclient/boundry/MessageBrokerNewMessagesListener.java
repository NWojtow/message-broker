package messagebrokerclient.boundry;

import constants.MessageBrokerConstants;
import messagebrokerclient.control.MessageBrokerNewMessagesControler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.ServerSocket;

@Component
public class MessageBrokerNewMessagesListener {
    private ServerSocket serverSocket;

    @PostConstruct
    public void start() {
        try {
            serverSocket = new ServerSocket(Integer.valueOf(MessageBrokerConstants.CLIENT_PORT));
            while (true) {
                new MessageBrokerNewMessagesControler(serverSocket.accept());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
