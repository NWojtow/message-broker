package messagebrokerclient.boundry;

import constants.MessageBrokerConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.Socket;

@Component
public class MessageBrokerOldMessagesListener {
    @PostConstruct
    public void start() {
        try {
            Socket socket = new Socket(MessageBrokerConstants.SERVER_ADDRESS, Integer.valueOf(MessageBrokerConstants.SERVER_PORT));
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            printWriter.println("Admin2");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
