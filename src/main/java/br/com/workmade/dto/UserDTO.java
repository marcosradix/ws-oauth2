package br.com.workmade.dto;

import br.com.workmade.domain.Role;
import br.com.workmade.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isEnabled;

    @DBRef(lazy = true)
    private List<Role> roles = new ArrayList<>();

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.isEnabled = user.getIsEnabled();
        this.roles = user.getRoles();
    }
}
