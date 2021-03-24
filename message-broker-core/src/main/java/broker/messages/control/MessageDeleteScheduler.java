package broker.messages.control;

import broker.datasource.entities.MessageDAO;
import broker.datasource.services.MessageService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MessageDeleteScheduler {

    private MessageDAO messageDAO;
    private TaskScheduler scheduler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MessageService messageService = new MessageService();
            messageService.delete(messageDAO);
        }
    };

    public MessageDeleteScheduler(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        scheduleDeleteMessage();
    }

    @Async
    public void scheduleDeleteMessage() {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        scheduler.schedule(runnable, messageDAO.getExpiration_date());

    }
}