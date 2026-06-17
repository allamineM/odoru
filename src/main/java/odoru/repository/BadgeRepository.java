package odoru.repository;

import odoru.entities.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends MongoRepository<Badge, String> {

    Optional<Badge> findByNumeroBadge(String numeroBadge);
    Optional<Badge> findByMembreId(String membreId);
    boolean existsByNumeroBadge(String numeroBadge);
}
