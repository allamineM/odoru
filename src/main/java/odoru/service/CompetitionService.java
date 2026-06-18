package odoru.service;

import odoru.entities.Competition;
import odoru.entities.Utilisateur;
import odoru.repository.CompetitionRepository;
import odoru.repository.UtilisateurRepository;
import odoru.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Competition createCompetition(Competition competition, String enseignantId) {
        if (competition.getDateHeure().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new DateInvalideException("La date doit être au moins 7 jours après aujourd'hui");
        }

        Utilisateur enseignant = utilisateurRepository.findById(enseignantId)
                .orElseThrow(() -> new UtilisateurIntrouvableException("Enseignant introuvable"));

        if (!enseignant.getRoles().contains(Utilisateur.Role.TEACHER)) {
            throw new RoleInvalideException("Cet utilisateur n'est pas enseignant");
        }

        if (enseignant.getNiveauExpertise() < competition.getNiveauCible()) {
            throw new AptitudeInsuffisanteException("L'enseignant n'est pas apte au niveau " + competition.getNiveauCible());
        }

        competition.setEnseignantId(enseignantId);
        return competitionRepository.save(competition);
    }

    public Competition addResultat(String competitionId, String membreId, double note) {
        if (note < 0 || note > 10) {
            throw new NoteInvalideException("La note doit être entre 0 et 10");
        }

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new CompetitionIntrouvableException("Compétition introuvable"));

        Utilisateur membre = utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new UtilisateurIntrouvableException("Membre introuvable"));

        if (membre.getNiveauExpertise() < competition.getNiveauCible()) {
            throw new AptitudeInsuffisanteException("Le niveau de l'élève (" + membre.getNiveauExpertise() + ") est insuffisant pour cette compétition (Niveau " + competition.getNiveauCible() + ")");
        }

        double noteArrondie = Math.round(note * 10.0) / 10.0;

        competition.getResultats().put(membreId, noteArrondie);
        return competitionRepository.save(competition);
    }

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public List<Competition> getCompetitionsByNiveau(int niveau) {
        return competitionRepository.findByNiveauCible(niveau);
    }

    public List<Competition> getCompetitionsByMembre(String membreId) {
        return competitionRepository.findAll().stream()
                .filter(c -> c.getResultats().containsKey(membreId))
                .toList();
    }

    public List<Competition> getCompetitionsByEnseignant(String enseignantId) {
        return competitionRepository.findByEnseignantId(enseignantId);
    }
}