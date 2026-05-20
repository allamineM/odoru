package odoru.service;

import odoru.domain.Course;
import odoru.domain.User;
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

    public Course createCourse(Course course, String enseignantId) {
        if (course.getDateHeure().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new RuntimeException("La date du cours doit être au moins 7 jours après aujourd'hui");
        }

        User enseignant = userRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        if (!enseignant.getRoles().contains(User.Role.TEACHER)) {
            throw new RuntimeException("Cet utilisateur n'est pas enseignant");
        }

        if (enseignant.getNiveauExpertise() < course.getNiveauCible()) {
            throw new RuntimeException("L'enseignant n'est pas apte au niveau " + course.getNiveauCible());
        }

        course.setEnseignantId(enseignantId);
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByNiveau(int niveau) {
        return courseRepository.findByNiveauCible(niveau);
    }

    public List<Course> getCoursesByEnseignant(String enseignantId) {
        return courseRepository.findByEnseignantId(enseignantId);
    }

    public List<Course> getCoursesByMembre(String membreId) {
        User membre = userRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        return courseRepository.findByNiveauCible(membre.getNiveauExpertise());
    }
}
