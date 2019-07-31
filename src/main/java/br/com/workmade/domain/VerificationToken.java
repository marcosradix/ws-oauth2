package br.com.workmade.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Document
public class VerificationToken extends GenericSerializable {
    private static  final int EXPIRATION = 60 * 24;
    @Id
    private String id;
    private String token;

    @DBRef(lazy = true)
    private User user;

    private Date expiryDate;

    public VerificationToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDateToken(EXPIRATION);
    }

    public VerificationToken(final String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDateToken(EXPIRATION);
    }

    private Date calculateExpiryDateToken(final int expiryTimeInMinutes){
        final var calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
