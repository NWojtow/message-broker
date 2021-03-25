package broker.users.control;

import constants.MessageBrokerConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.ServerSocket;

@Component
public class UserSocketEndpoint {
    private ServerSocket serverSocket;

    @PostConstruct
    public void start() {
        try {
            serverSocket = new ServerSocket(Integer.valueOf(MessageBrokerConstants.SERVER_PORT));
            while (true) {
                new ClientHandler(serverSocket.accept());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
