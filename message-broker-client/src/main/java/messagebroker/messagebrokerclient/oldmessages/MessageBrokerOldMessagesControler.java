package messagebroker.messagebrokerclient.oldmessages;

import constants.MessageBrokerConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageBrokerOldMessagesControler implements Runnable {
    Thread thread;

    public MessageBrokerOldMessagesControler() {
        Double id = Math.random();
        this.thread = new Thread(this, id.toString());
        thread.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
            Socket socket = new Socket(MessageBrokerConstants.SERVER_ADDRESS, Integer.valueOf(MessageBrokerConstants.SERVER_PORT));
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            printWriter.println("Admin2");

            String inputLine;
            System.out.println("Old messages");
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


