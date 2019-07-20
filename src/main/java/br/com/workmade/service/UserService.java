package br.com.workmade.service;

import br.com.workmade.domain.User;
import br.com.workmade.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
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
        LOGGER.info("Salvando user.");
        return this.userRepository.saveAll(users);
    }
}
