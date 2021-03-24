package broker.application.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.lang.model.UnknownEntityException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(EntityNotFoundException e, WebRequest request){
        return new ResponseEntity("Entity not found", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value={RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e, WebRequest request){
        return new ResponseEntity("Runtime exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value={EntityExistsException.class})
    public ResponseEntity<Object> handleEntityExistsException(EntityExistsException e, WebRequest request){
        return new ResponseEntity("Entity already exists exception", HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = {UnknownEntityException.class})
    public ResponseEntity<Object> handleUnknownEntity(UnknownEntityException e, WebRequest request){
        return new ResponseEntity("Unknown entity exception", HttpStatus.NOT_FOUND);
    }
}
