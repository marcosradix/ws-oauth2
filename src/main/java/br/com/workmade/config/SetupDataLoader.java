package br.com.workmade.config;

import br.com.workmade.domain.Role;
import br.com.workmade.domain.User;
import br.com.workmade.service.RoleService;
import br.com.workmade.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.Optional;

@Configuration
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private RoleService roleService;


    public SetupDataLoader(
            UserService userService,
            RoleService roleService
    ) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role role_admin = createRoleIfNotFound("ROLE_ADMIN");
        Role role_user = createRoleIfNotFound("ROLE_USER");


        User joao = new User(null, "João", "Souza", "joao@gmail.com", Arrays.asList(role_user));
        User maria = new User(null, "Eliezer", "Mendes", "eli.mendes@gmail.com", Arrays.asList(role_admin,role_user));
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

    public Role createRoleIfNotFound(String name){
        Optional<Role> roleByName = this.roleService.findRoleByName(name);
        if(roleByName.isPresent()){
            return  roleByName.get();
        }
        return this.roleService.createRole(name);

    }

}
