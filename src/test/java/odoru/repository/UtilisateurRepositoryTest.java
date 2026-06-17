package odoru.repository;

import odoru.entities.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour UtilisateurRepository
 * C'est un test d'intégration avec une base de données MongoDB embarquée
 */
@DataMongoTest
class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    void testFindByNomUtilisateur() {
        assertNotNull(utilisateurRepository);

        // On crée et on insère un utilisateur en base
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("Sadfi");
        utilisateur.setPrenom("Youssef");
        utilisateur.setNomUtilisateur("ysadfi");
        utilisateur.setEmail("youssef@test.com");
        utilisateurRepository.save(utilisateur);

        assertNotNull(utilisateur.getId());

        // On teste la méthode de recherche
        Optional<Utilisateur> trouve = utilisateurRepository.findByNomUtilisateur("ysadfi");

        // On vérifie le résultat
        assertTrue(trouve.isPresent());
        assertEquals("Sadfi", trouve.get().getNom());

        // On teste avec un nom d'utilisateur inexistant
        Optional<Utilisateur> nonTrouve = utilisateurRepository.findByNomUtilisateur("inconnu");
        assertFalse(nonTrouve.isPresent());
    }
}