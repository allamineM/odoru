package odoru.repository;

import odoru.entities.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends MongoRepository<Competition, String> {

    List<Competition> findByNiveauCible(int niveauCible);
    List<Competition> findByEnseignantId(String enseignantId);
}
