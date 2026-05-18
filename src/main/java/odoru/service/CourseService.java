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

    public Course createCourse(Course course, String teacherId) {
        // Règle : la date doit être supérieure de 7 jours
        if (course.getDateTime().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new RuntimeException("La date du cours doit être au moins 7 jours après aujourd'hui");
        }

        // Règle : l'enseignant doit être apte au niveau du cours
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        if (!teacher.getRoles().contains(User.Role.TEACHER)) {
            throw new RuntimeException("Cet utilisateur n'est pas enseignant");
        }

        if (teacher.getExpertiseLevel() < course.getTargetLevel()) {
            throw new RuntimeException("L'enseignant n'est pas apte au niveau " + course.getTargetLevel());
        }

        course.setTeacherId(teacherId);
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByLevel(int level) {
        return courseRepository.findByTargetLevel(level);
    }

    public List<Course> getCoursesByTeacher(String teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    // Cours d'un élève = cours de son niveau
    public List<Course> getCoursesByMember(String memberId) {
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        return courseRepository.findByTargetLevel(member.getExpertiseLevel());
    }
}
