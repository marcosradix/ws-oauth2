package br.com.workmade.Resource;

import br.com.workmade.domain.Role;
import br.com.workmade.domain.User;
import br.com.workmade.dto.UserDTO;
import br.com.workmade.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class UserResouruce {

    private UserService userService;
    public UserResouruce(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> users = this.userService.findUsers();
        List<UserDTO> listUserDTO = users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listUserDTO);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDTO> findOne(@PathVariable(value = "id") String id){
        User user = this.userService.findById(id);
        UserDTO userDTO =  this.userService.userDTOFromUser(user);
        return ResponseEntity.ok().body(userDTO);
    }


    @PostMapping(value = "users")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO){
        User user = this.userService.userFromDTO(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(this.userService.saveUser(user)));

    }


    @PutMapping(value = "users/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id,@RequestBody UserDTO userDTO){
        User user = this.userService.userFromDTO(userDTO);
        user.setId(id);
        return ResponseEntity.ok().body(new UserDTO(this.userService.updateUser(user)));

    }
    @DeleteMapping(value = "users/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.userService.findById(id);
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping(value = "users/{id}/roles")
    public ResponseEntity<List<Role>> findRoles(@PathVariable String id){
        User user =  userService.findById(id);
        return ResponseEntity.ok().body(user.getRoles());
    }

}
