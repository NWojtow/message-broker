package broker.users.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.ServerSocket;

@RunWith(MockitoJUnitRunner.class)
public class UserSocketEndpoint {

    @Mock
    ServerSocket serverSocket;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSendNewMessage() {
        new UserNewMessageSocketEndpoint("message", "127.0.0.1");
    }

    @Test
    public void shouldRecieveNewMessage() throws IOException {
        new ClientHandler(serverSocket.accept());
    }
}
