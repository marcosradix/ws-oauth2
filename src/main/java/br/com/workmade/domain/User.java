package br.com.workmade.domain;

import br.com.workmade.dto.UserDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = {"id"})
@Data
@NoArgsConstructor
@Document(collection="user")
public class User extends GenericSerializable {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isEnabled;

    public User(User user) {
        super();
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.isEnabled = user.getIsEnabled();

    }

    @DBRef(lazy = true)
    private List<Role> roles = new ArrayList<>();

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
    }

    public User(String id, String firstName, String lastName, String email, String password, boolean isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public User(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
