package broker.configuration.jwt.service;

import broker.configuration.jwt.entities.UserDTO;
import broker.datasource.entities.UserDAO;
import broker.datasource.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JWTUserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bCryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserService userService = new UserService();
        Optional<UserDAO> user = userService.findByUsername(username);

            if(user.isPresent()) {
                return new User(user.get().getUsername(), user.get().getPasswd(), getAutorities(user.get()));
            } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
    public UserDAO save(UserDTO userData){
        UserService userService = new UserService();

        UserDAO newUser = new UserDAO();
        newUser.setUsername(userData.getUsername());
        newUser.setPasswd(bCryptEncoder.encode(userData.getPassword()));
        newUser.setAddress(userData.getAddress());
        newUser.setType("USER");

        return userService.save(newUser);
    }

    public UserDAO saveAdmin(UserDTO userData){
        UserService userService = new UserService();

        broker.datasource.entities.UserDAO newUserDTO = new UserDAO();
        newUserDTO.setUsername(userData.getUsername());
        newUserDTO.setPasswd(bCryptEncoder.encode(userData.getPassword()));
        newUserDTO.setType("ADMIN");
        newUserDTO.setAddress(userData.getAddress());

        return userService.save(newUserDTO);
    }

    private Collection getAutorities(UserDAO user){
        Set<SimpleGrantedAuthority> autorities = new HashSet();

        autorities.add(new SimpleGrantedAuthority("ROLE_" + user.getType()));

        return autorities;
    }
}
