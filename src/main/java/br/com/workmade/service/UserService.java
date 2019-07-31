package br.com.workmade.service;

import br.com.workmade.domain.User;
import br.com.workmade.domain.VerificationToken;
import br.com.workmade.dto.UserDTO;
import br.com.workmade.exception.ObjectAlreadyExistsException;
import br.com.workmade.exception.ObjectNotFoundException;
import br.com.workmade.repository.RoleRepository;
import br.com.workmade.repository.UserRepository;

import br.com.workmade.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;


    public List<User> findUsers() {
        return this.userRepository.findAll();
    }

    public User saveUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        log.info("Salvando user.");
        return this.userRepository.saveAll(users);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User findById(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Dado não encontrado."));
        return user;
    }

    public User userFromDTO(UserDTO userDTO) {
        return new User(userDTO);
    }

    public UserDTO userDTOFromUser(User user) {
        return new UserDTO(user);
    }

    public User updateUser(User user) {
        findById(user.getId());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public void deleteUser(String id) {
        this.userRepository.deleteById(id);
    }

    public User registerUser(User user) {
        if (emailExits(user.getEmail())) {
            throw new ObjectAlreadyExistsException(String.format("Já existe uma conta com o endereço de email informado", user.getEmail()));
        }

        user.setRoles(Arrays.asList(this.roleRepository.findByName("ROLE_USER").get()));
        User userCreated = saveUser(user);
        return userCreated;
    }

    private boolean emailExits(final String email) {
        Optional<User> emailUser = this.userRepository.findByEmail(email);
        return emailUser.isPresent();
    }

    public void createVerificationTokenForUser(User user, String token){
        final var vToken = new VerificationToken();
        this.verificationTokenRepository.save(vToken);
    }
}
