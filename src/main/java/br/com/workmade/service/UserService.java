package br.com.workmade.service;

import br.com.workmade.domain.User;
import br.com.workmade.dto.UserDTO;
import br.com.workmade.exception.ObjectNotFoundException;
import br.com.workmade.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserService {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsers(){
        return this.userRepository.findAll();
    }

    public User saveUser(User user){
        return this.userRepository.save(user);
    }

    public List<User> saveUsers(List<User> users){
        log.info("Salvando user.");
        return this.userRepository.saveAll(users);
    }

    public Optional<User> findByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public UserDTO findById(String id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Dado não encontrado."));
        return new UserDTO(user );
    }

    public User userFromDTO(UserDTO userDTO){
        return new User(userDTO);
    }

}
