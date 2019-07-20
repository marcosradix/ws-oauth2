package br.com.workmade.Resource;

import br.com.workmade.domain.User;
import br.com.workmade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserResouruce {

    private UserService userService;
    public UserResouruce(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<User>> findAll(){
        List<User> users = this.userService.findUsers();
        return ResponseEntity.ok().body(users);
    }

}
