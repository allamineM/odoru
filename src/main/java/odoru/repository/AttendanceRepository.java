package odoru.repository;

import odoru.domain.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    List<Attendance> findByMembreId(String membreId);
    List<Attendance> findByCoursId(String coursId);
    boolean existsByMembreIdAndCoursId(String membreId, String coursId);
}
