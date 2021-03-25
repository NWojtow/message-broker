package broker.messages.boundry;

import broker.datasource.entities.MessageDAO;
import broker.datasource.services.MessageService;
import broker.entities.MessageDTO;
import broker.messages.control.MessageDeleteScheduler;
import broker.messages.control.MessageHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

@RestController
@CrossOrigin
public class MessagesController {

    private MessageService messageService = new MessageService();

    @ResponseBody
    @RequestMapping(value = "/message", method = RequestMethod.PUT)
    public ResponseEntity<String> putNewMessage(@Valid @RequestBody MessageDTO message) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(message);

        MessageDAO messageDAO = messageService.save(message);
        new MessageHandler(message.getSubjectType(), message.getMessage());
        new MessageDeleteScheduler(messageDAO);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMessage(@PathVariable("id") int id) {
        messageService.deleteById(id);
        return  new ResponseEntity<>(null, HttpStatus.OK);
    }
}
