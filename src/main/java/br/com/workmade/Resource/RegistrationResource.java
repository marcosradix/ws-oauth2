package br.com.workmade.Resource;

import br.com.workmade.domain.User;
import br.com.workmade.dto.UserDTO;
import br.com.workmade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class RegistrationResource {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/public/registration/users")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        User user  = this.userService.userFromDTO(userDTO);
        User userRegistered= this.userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistered);
    }
}
