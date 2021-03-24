package broker.users.control;

import java.io.IOException;
import java.net.ServerSocket;

public class UserSocketEndpoint {
    private ServerSocket serverSocket;

    public void start (int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while(true) {
            new ClientHandler(serverSocket.accept());
        }
    }
}
