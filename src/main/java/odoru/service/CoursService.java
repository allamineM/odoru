package odoru.service;

import odoru.entities.Cours;
import odoru.entities.Utilisateur;
import odoru.repository.CoursRepository;
import odoru.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CoursService {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Cours createCours(Cours cours, String enseignantId) {
        if (cours.getDateHeure().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new RuntimeException("La date du cours doit être au moins 7 jours après aujourd'hui");
        }

        Utilisateur enseignant = utilisateurRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        if (!enseignant.getRoles().contains(Utilisateur.Role.TEACHER)) {
            throw new RuntimeException("Cet utilisateur n'est pas enseignant");
        }

        if (enseignant.getNiveauExpertise() < cours.getNiveauCible()) {
            throw new RuntimeException("L'enseignant n'est pas apte au niveau " + cours.getNiveauCible());
        }

        cours.setEnseignantId(enseignantId);
        return coursRepository.save(cours);
    }

    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    public List<Cours> getCoursByNiveau(int niveau) {
        return coursRepository.findByNiveauCible(niveau);
    }

    public List<Cours> getCoursByEnseignant(String enseignantId) {
        return coursRepository.findByEnseignantId(enseignantId);
    }

    public List<Cours> getCoursByMembre(String membreId) {
        Utilisateur membre = utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        return coursRepository.findByNiveauCible(membre.getNiveauExpertise());
    }
}