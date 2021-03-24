package broker.users.control;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.ServerSocket;

@Component
public class UserSocketEndpoint {
    private ServerSocket serverSocket;

    @PostConstruct
    public void start() {
        try {
            serverSocket = new ServerSocket(5555);
            while (true) {
                new ClientHandler(serverSocket.accept());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
