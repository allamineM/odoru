package odoru.service;

import odoru.entities.Presence;
import odoru.entities.Cours;
import odoru.entities.Utilisateur;
import odoru.repository.PresenceRepository;
import odoru.repository.CompetitionRepository;
import odoru.repository.CoursRepository;
import odoru.repository.UtilisateurRepository;
import odoru.utilities.UtilisateurIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StatistiqueService {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Map<String, Object> getNombreCoursEtMoyennePresences() {
        List<Cours> cours = coursRepository.findAll();
        long nombreCours = cours.size();

        double moyenne = cours.stream()
                .mapToInt(c -> presenceRepository.findByCoursId(c.getId()).size())
                .average()
                .orElse(0.0);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("nombreCours", nombreCours);
        result.put("moyenneElevesPresents", Math.round(moyenne * 10.0) / 10.0);
        return result;
    }

    public Map<String, Object> getElevesParCours(String coursId) {
        List<Presence> presences = presenceRepository.findByCoursId(coursId);

        List<Map<String, String>> eleves = presences.stream().map(p -> {
            Map<String, String> eleve = new LinkedHashMap<>();
            eleve.put("membreId", p.getMembreId());
            utilisateurRepository.findById(p.getMembreId()).ifPresent(u -> {
                eleve.put("nom", u.getNom());
                eleve.put("prenom", u.getPrenom());
            });
            return eleve;
        }).toList();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("coursId", coursId);
        result.put("nombrePresents", presences.size());
        result.put("eleves", eleves);
        return result;
    }

    public List<Map<String, Object>> getCoursMembreAvecPresences(String membreId, LocalDateTime debut, LocalDateTime fin) {
        List<Cours> cours = coursRepository.findAll().stream()
                .filter(c -> {
                    if (debut != null && c.getDateHeure().isBefore(debut)) return false;
                    if (fin != null && c.getDateHeure().isAfter(fin)) return false;
                    return true;
                }).toList();

        Utilisateur membre = utilisateurRepository.findById(membreId)
                .orElseThrow(() -> new UtilisateurIntrouvableException("Membre introuvable"));

        return cours.stream()
                .filter(c -> c.getNiveauCible() == membre.getNiveauExpertise())
                .map(c -> {
                    boolean present = presenceRepository.existsByMembreIdAndCoursId(membreId, c.getId());
                    Map<String, Object> entry = new LinkedHashMap<>();
                    entry.put("coursId", c.getId());
                    entry.put("titre", c.getTitre());
                    entry.put("dateHeure", c.getDateHeure());
                    entry.put("present", present);
                    return entry;
                }).toList();
    }

    public Map<String, Object> getNombreCompetitionsParNiveau(int niveau) {
        long nombre = competitionRepository.findByNiveauCible(niveau).size();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("niveau", niveau);
        result.put("nombreCompetitions", nombre);
        return result;
    }

    public List<Map<String, Object>> getCompetitionsMembreAvecResultats(String membreId, LocalDateTime debut, LocalDateTime fin) {
        return competitionRepository.findAll().stream()
                .filter(c -> c.getResultats().containsKey(membreId))
                .filter(c -> {
                    if (debut != null && c.getDateHeure().isBefore(debut)) return false;
                    if (fin != null && c.getDateHeure().isAfter(fin)) return false;
                    return true;
                })
                .map(c -> {
                    Map<String, Object> entry = new LinkedHashMap<>();
                    entry.put("competitionId", c.getId());
                    entry.put("titre", c.getTitre());
                    entry.put("dateHeure", c.getDateHeure());
                    entry.put("note", c.getResultats().get(membreId));
                    return entry;
                }).toList();
    }
}