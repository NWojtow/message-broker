package broker.users.boundry;

import broker.datasource.entities.UserDAO;
import broker.datasource.services.UserService;
import broker.entities.SubjectDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.util.Objects.nonNull;

@RestController
@CrossOrigin
public class UserController {

    private UserService userService = new UserService();

    @ResponseBody
    @RequestMapping(value = "/user/subject", method = RequestMethod.POST)
    public ResponseEntity<String> addSubjectToUser(@RequestBody SubjectDTO subjectDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(nonNull(currentPrincipalName)) {
            Optional<UserDAO> user = userService.findByUsername(currentPrincipalName);

            if(user.isPresent()) {
                userService.updateSubjects(subjectDTO.getSubjectType(), currentPrincipalName);
            }
            return  new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return  new ResponseEntity<>(null, HttpStatus.OK);
    }
}
