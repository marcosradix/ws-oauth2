package br.com.workmade.repository;

import br.com.workmade.domain.User;
import br.com.workmade.domain.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}
