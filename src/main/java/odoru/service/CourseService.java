package odoru.service;

import odoru.entities.Cours;
import odoru.entities.Utilisateur;
import odoru.repository.CourseRepository;
import odoru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public Cours createCourse(Cours cours, String enseignantId) {
        if (cours.getDateHeure().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new RuntimeException("La date du cours doit être au moins 7 jours après aujourd'hui");
        }

        Utilisateur enseignant = userRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        if (!enseignant.getRoles().contains(Utilisateur.Role.TEACHER)) {
            throw new RuntimeException("Cet utilisateur n'est pas enseignant");
        }

        if (enseignant.getNiveauExpertise() < cours.getNiveauCible()) {
            throw new RuntimeException("L'enseignant n'est pas apte au niveau " + cours.getNiveauCible());
        }

        cours.setEnseignantId(enseignantId);
        return courseRepository.save(cours);
    }

    public List<Cours> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Cours> getCoursesByNiveau(int niveau) {
        return courseRepository.findByNiveauCible(niveau);
    }

    public List<Cours> getCoursesByEnseignant(String enseignantId) {
        return courseRepository.findByEnseignantId(enseignantId);
    }

    public List<Cours> getCoursesByMembre(String membreId) {
        Utilisateur membre = userRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        return courseRepository.findByNiveauCible(membre.getNiveauExpertise());
    }
}
