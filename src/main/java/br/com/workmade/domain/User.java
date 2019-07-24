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
@AllArgsConstructor
@Document
@Builder
public class User implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    @DBRef(lazy = true)
    private List<Role> roles = new ArrayList<>();

    public User() {}

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
    }
}
