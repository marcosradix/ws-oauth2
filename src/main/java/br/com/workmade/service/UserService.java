package br.com.workmade.service;

import br.com.workmade.domain.User;
import br.com.workmade.dto.UserDTO;
import br.com.workmade.exception.ObjectNotFoundException;
import br.com.workmade.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserService {


    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUsers(){
        return this.userRepository.findAll();
    }

    public User saveUser(User user){
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public List<User> saveUsers(List<User> users){
        log.info("Salvando user.");
        return this.userRepository.saveAll(users);
    }

    public Optional<User> findByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User findById(String id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Dado não encontrado."));
        return user;
    }

    public User userFromDTO(UserDTO userDTO){
        return new User(userDTO);
    }

    public UserDTO userDTOFromUser(User user){
        return new UserDTO(user);
    }

    public User updateUser(User user){
        findById(user.getId());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public void deleteUser(String id){
        this.userRepository.deleteById(id);
    }
}
