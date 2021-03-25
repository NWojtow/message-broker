package messagebroker.messagebrokerclient.oldmessages;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageBrokerOldMessagesListener {

    @PostConstruct
    public void start() {
        new MessageBrokerOldMessagesControler();
    }
}
