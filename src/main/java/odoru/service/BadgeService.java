package odoru.service;

import odoru.entities.Presence;
import odoru.entities.Badge;
import odoru.entities.Cours;
import odoru.entities.Utilisateur;
import odoru.repository.PresenceRepository;
import odoru.repository.BadgeRepository;
import odoru.repository.CoursRepository;
import odoru.repository.UtilisateurRepository;
import odoru.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Badge assignerBadge(String numeroBadge, String membreId) {
        if (badgeRepository.existsByNumeroBadge(numeroBadge)) {
            throw new BadgeDejaAssocieException("Ce badge est déjà associé à un membre");
        }
        Badge badge = new Badge();
        badge.setNumeroBadge(numeroBadge);
        badge.setMembreId(membreId);
        return badgeRepository.save(badge);
    }

    public void retirerBadge(String numeroBadge) {
        Badge badge = badgeRepository.findByNumeroBadge(numeroBadge)
                .orElseThrow(() -> new BadgeIntrouvableException("Badge introuvable"));
        badgeRepository.delete(badge);
    }

    public Presence scanner(String numeroBadge, String coursId) {
        Badge badge = badgeRepository.findByNumeroBadge(numeroBadge)
                .orElseThrow(() -> new BadgeIntrouvableException("Badge inconnu"));

        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new CoursIntrouvableException("Cours introuvable"));

        coursRepository.findById(coursId)
                .orElseThrow(() -> new CoursIntrouvableException("Cours introuvable"));

        Utilisateur membre = utilisateurRepository.findById(badge.getMembreId())
                .orElseThrow(() -> new UtilisateurIntrouvableException("Membre introuvable"));

        if (membre.getNiveauExpertise() < cours.getNiveauCible()) {
            throw new AptitudeInsuffisanteException("Niveau insuffisant. Requis : " + cours.getNiveauCible() + ", Actuel : " + membre.getNiveauExpertise());
        }

        if (presenceRepository.existsByMembreIdAndCoursId(badge.getMembreId(), coursId)) {
            throw new PresenceDejaEnregistreeException("Présence déjà enregistrée");
        }

        Presence presence = new Presence();
        presence.setMembreId(badge.getMembreId());
        presence.setCoursId(coursId);
        return presenceRepository.save(presence);
    }

    public List<Cours> getCoursSuivisParMembre(String membreId) {
        return presenceRepository.findByMembreId(membreId).stream()
                .map(a -> coursRepository.findById(a.getCoursId()).orElse(null))
                .filter(c -> c != null)
                .toList();
    }

    public List<Presence> getPresencesParCours(String coursId) {
        return presenceRepository.findByCoursId(coursId);
    }
}