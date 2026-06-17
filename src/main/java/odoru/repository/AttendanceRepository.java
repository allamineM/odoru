package odoru.repository;

import odoru.entities.Presence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends MongoRepository<Presence, String> {

    List<Presence> findByMembreId(String membreId);
    List<Presence> findByCoursId(String coursId);
    boolean existsByMembreIdAndCoursId(String membreId, String coursId);
}
