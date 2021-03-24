package broker.users.control;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;

@Component
public class UserSocketEndpoint {
    private ServerSocket serverSocket;

    @PostConstruct
    public void start (int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while(true) {
            new ClientHandler(serverSocket.accept());
        }
    }
}
