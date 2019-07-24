package br.com.workmade.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@Document
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String name;

}
