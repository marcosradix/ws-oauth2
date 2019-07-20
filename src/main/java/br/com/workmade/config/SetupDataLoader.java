package br.com.workmade.config;

import br.com.workmade.domain.User;
import br.com.workmade.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Optional;

@Configuration
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;

    public SetupDataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
       User joao = new User(null, "João", "Souza", "joao@gmail.com");
        User maria = new User(null, "Eliezer", "Mendes", "eli.mendes@gmail.com");
        User userIfNotExists1 = createUserIfNotExists(joao);
        User userIfNotExists2 = createUserIfNotExists(maria);


    }

    public User createUserIfNotExists(final User user) {
        log.info("Verificando se email existe " + user.getEmail());
        Optional<User> email = this.userService.findByEmail(user.getEmail());
        User userFounded = email.orElse(null);
        if (userFounded == null) {
            User userSaved = this.userService.saveUser(user);
            log.info("Usuário salvo "+userSaved);
            return userSaved;
        }

        return null;

    }


}
