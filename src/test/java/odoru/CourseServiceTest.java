package odoru;

import odoru.domain.Course;
import odoru.domain.User;
import odoru.repository.CourseRepository;
import odoru.repository.UserRepository;
import odoru.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseService courseService;

    private User enseignant;
    private Course cours;

    @BeforeEach
    void setUp() {
        enseignant = new User();
        enseignant.setNom("Dupont");
        enseignant.setNiveauExpertise(5);
        enseignant.getRoles().add(User.Role.TEACHER);

        cours = new Course();
        cours.setTitre("Danse niveau 5");
        cours.setNiveauCible(5);
        cours.setDateHeure(LocalDateTime.now().plusDays(10));
        cours.setLieu("Salle A");
        cours.setDureeMinutes(60);
    }

    @Test
    void createCourse_succes() {
        when(userRepository.findById("1")).thenReturn(Optional.of(enseignant));
        when(courseRepository.save(any(Course.class))).thenReturn(cours);

        Course result = courseService.createCourse(cours, "1");

        assertNotNull(result);
        verify(courseRepository, times(1)).save(cours);
    }

    @Test
    void createCourse_dateTropProche() {
        cours.setDateHeure(LocalDateTime.now().plusDays(3));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.createCourse(cours, "1"));

        assertEquals("La date du cours doit être au moins 7 jours après aujourd'hui", ex.getMessage());
        verify(courseRepository, never()).save(any());
    }

    @Test
    void createCourse_enseignantPasApteAuNiveau() {
        enseignant.setNiveauExpertise(3);
        when(userRepository.findById("1")).thenReturn(Optional.of(enseignant));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.createCourse(cours, "1"));

        assertEquals("L'enseignant n'est pas apte au niveau 5", ex.getMessage());
        verify(courseRepository, never()).save(any());
    }

    @Test
    void createCourse_pasEnseignant() {
        enseignant.getRoles().remove(User.Role.TEACHER);
        when(userRepository.findById("1")).thenReturn(Optional.of(enseignant));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.createCourse(cours, "1"));

        assertEquals("Cet utilisateur n'est pas enseignant", ex.getMessage());
        verify(courseRepository, never()).save(any());
    }

    @Test
    void getCoursesByNiveau_retourneListe() {
        when(courseRepository.findByNiveauCible(5)).thenReturn(List.of(cours));

        List<Course> result = courseService.getCoursesByNiveau(5);

        assertEquals(1, result.size());
        verify(courseRepository, times(1)).findByNiveauCible(5);
    }
}
