package broker.messages.control;

import broker.datasource.entities.MessageDAO;
import broker.datasource.entities.SubjectDAO;
import broker.datasource.services.MessageService;
import broker.datasource.services.SubjectService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class TestMessageScheduler implements Runnable {
    SubjectService subjectService = new SubjectService();
    MessageService messageService = new MessageService();
    SubjectDAO subject = subjectService.getSubjectBySubjectType("AUTOMOTIVE").get();

    @Override
    @PostConstruct
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
            currentTime.setTime(currentTime.getTime() + TimeUnit.MINUTES.toMillis(10));
            MessageDAO testMessage = new MessageDAO("test message: " + String.valueOf(Math.random()),
                    currentTime, subject);
            messageService.save(testMessage);
            new MessageEndpoint("AUTOMOTIVE", testMessage.getMessage());
            new MessageDeleteScheduler(testMessage);
        }
    }
}
