package broker.users.control;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class UserNewMessageEndpoint implements Runnable {
    private String message;
    private String address;
    private Thread thread;

    public UserNewMessageEndpoint(String message, String address) {
        this.message = message;
        this.address = address;
        thread = new Thread(this, message + address);
        thread.start();
    }
    @Override
    public void run() {

            try {
                Socket socket = new Socket(address, 1567);
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);
                printWriter.println(message);

                printWriter.close();
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
