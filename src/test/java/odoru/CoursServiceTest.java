package odoru;

import odoru.entities.Cours;
import odoru.entities.Utilisateur;
import odoru.repository.CoursRepository;
import odoru.repository.UtilisateurRepository;
import odoru.service.CoursService;
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
public class CoursServiceTest {

    @Mock
    private CoursRepository coursRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private CoursService coursService;

    private Utilisateur enseignant;
    private Cours cours;

    @BeforeEach
    void setUp() {
        enseignant = new Utilisateur();
        enseignant.setNom("Dupont");
        enseignant.setNiveauExpertise(5);
        enseignant.getRoles().add(Utilisateur.Role.TEACHER);

        cours = new Cours();
        cours.setTitre("Danse niveau 5");
        cours.setNiveauCible(5);
        cours.setDateHeure(LocalDateTime.now().plusDays(10));
        cours.setLieu("Salle A");
        cours.setDureeMinutes(60);
    }

    @Test
    void createCourse_succes() {
        when(utilisateurRepository.findById("1")).thenReturn(Optional.of(enseignant));
        when(coursRepository.save(any(Cours.class))).thenReturn(cours);

        Cours result = coursService.createCourse(cours, "1");

        assertNotNull(result);
        verify(coursRepository, times(1)).save(cours);
    }

    @Test
    void createCourse_dateTropProche() {
        cours.setDateHeure(LocalDateTime.now().plusDays(3));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> coursService.createCourse(cours, "1"));

        assertEquals("La date du cours doit être au moins 7 jours après aujourd'hui", ex.getMessage());
        verify(coursRepository, never()).save(any());
    }

    @Test
    void createCourse_enseignantPasApteAuNiveau() {
        enseignant.setNiveauExpertise(3);
        when(utilisateurRepository.findById("1")).thenReturn(Optional.of(enseignant));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> coursService.createCourse(cours, "1"));

        assertEquals("L'enseignant n'est pas apte au niveau 5", ex.getMessage());
        verify(coursRepository, never()).save(any());
    }

    @Test
    void createCourse_pasEnseignant() {
        enseignant.getRoles().remove(Utilisateur.Role.TEACHER);
        when(utilisateurRepository.findById("1")).thenReturn(Optional.of(enseignant));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> coursService.createCourse(cours, "1"));

        assertEquals("Cet utilisateur n'est pas enseignant", ex.getMessage());
        verify(coursRepository, never()).save(any());
    }

    @Test
    void getCoursesByNiveau_retourneListe() {
        when(coursRepository.findByNiveauCible(5)).thenReturn(List.of(cours));

        List<Cours> result = coursService.getCoursesByNiveau(5);

        assertEquals(1, result.size());
        verify(coursRepository, times(1)).findByNiveauCible(5);
    }
}
