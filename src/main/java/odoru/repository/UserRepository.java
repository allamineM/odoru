package odoru.repository;

import odoru.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByNomUtilisateur(String nomUtilisateur);
    Optional<User> findByEmail(String email);
    boolean existsByNomUtilisateur(String nomUtilisateur);
    boolean existsByEmail(String email);
}
