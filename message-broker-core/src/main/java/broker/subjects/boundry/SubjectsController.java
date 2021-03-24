package broker.subjects.boundry;

import broker.datasource.services.SubjectService;
import broker.entities.SubjectDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

@RestController
@CrossOrigin
public class SubjectsController {

    private SubjectService subjectService = new SubjectService();

    @ResponseBody
    @RequestMapping(value = "/subject", method = RequestMethod.PUT)
    public ResponseEntity<String> putNewMessage(@Valid @RequestBody SubjectDTO subject) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validator.validate(subject);

        subjectService.save(subject.getSubjectType());

        return  new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMessageById(@PathVariable("id") int id) {
        subjectService.deleteById(id);

        return  new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
