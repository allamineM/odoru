package odoru.repository;

import odoru.domain.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends MongoRepository<Badge, String> {

    Optional<Badge> findByBadgeNumber(String badgeNumber);
    Optional<Badge> findByMemberId(String memberId);
    boolean existsByBadgeNumber(String badgeNumber);
}
