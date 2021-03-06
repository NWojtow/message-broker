package broker.messages.control;

import broker.datasource.entities.SubjectDAO;
import broker.datasource.entities.UserDAO;
import broker.datasource.services.SubjectService;
import broker.users.control.UserNewMessageSocketEndpoint;

import javax.persistence.EntityNotFoundException;

public class MessageHandler implements Runnable {
    private String subjectType;
    private String message;
    private Thread thread;

    public MessageHandler(String subjectType, String message) {
        this.subjectType = subjectType;
        this.message = message;
        this.thread = new Thread(this, "New message: "+ subjectType);
        thread.start();
    }

    @Override
    public void run() {
        SubjectService subjectService = new SubjectService();
        SubjectDAO subject = subjectService.getSubjectBySubjectType(subjectType).orElseThrow(EntityNotFoundException::new);

        for(UserDAO user: subject.getUsers()) {
            new UserNewMessageSocketEndpoint(message, user.getAddress());
        }
    }
}
