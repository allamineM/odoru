package odoru.repository;

import odoru.entities.Cours;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursRepository extends MongoRepository<Cours, String> {

    List<Cours> findByNiveauCible(int niveauCible);
    List<Cours> findByEnseignantId(String enseignantId);
}
