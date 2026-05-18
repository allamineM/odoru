package odoru.repository;

import odoru.domain.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends MongoRepository<Competition, String> {

    List<Competition> findByTargetLevel(int targetLevel);
    List<Competition> findByTeacherId(String teacherId);
}
