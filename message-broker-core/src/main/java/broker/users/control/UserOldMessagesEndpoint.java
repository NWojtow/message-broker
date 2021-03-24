package broker.users.control;

import broker.datasource.entities.MessageDAO;
import broker.datasource.entities.SubjectDAO;
import broker.datasource.entities.UserDAO;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class UserOldMessagesEndpoint implements Runnable{
    private Thread thread;
    private UserDAO user;

    public UserOldMessagesEndpoint(UserDAO user) {
        this.user = user;
        this.thread = new Thread(this, user.getUsername());
        thread.start();
    }

    @Override
    public void run() {
        String[] ip = user.getAddress().split(":");

        if (ip.length == 2) {
            try {
                Socket socket = new Socket(ip[0], Integer.valueOf(ip[1]));
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);

                for (SubjectDAO subject : user.getSubjects()) {
                    for (MessageDAO message : subject.getMessages()) {
                        printWriter.println(message.getMessage());
                    }
                    printWriter.close();
                    outputStream.close();
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
