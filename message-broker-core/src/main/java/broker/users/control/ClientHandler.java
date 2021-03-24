package broker.users.control;

import broker.datasource.entities.UserDAO;
import broker.datasource.services.UserService;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private UserService userService = new UserService();
    private Thread thread;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        Double id = Math.random();
        this.thread = new Thread(this, id.toString());
        thread.start();
    }

    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String userName = in.readLine();
            UserDAO user = userService.findByUsername(userName).orElseThrow(EntityNotFoundException::new);
            new UserOldMessagesEndpoint(user);

            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
