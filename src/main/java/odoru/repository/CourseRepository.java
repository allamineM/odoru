package odoru.repository;

import odoru.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findByNiveauCible(int niveauCible);
    List<Course> findByEnseignantId(String enseignantId);
}
