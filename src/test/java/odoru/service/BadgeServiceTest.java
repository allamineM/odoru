package odoru.service;

import odoru.entities.Presence;
import odoru.entities.Badge;
import odoru.entities.Cours;
import odoru.repository.PresenceRepository;
import odoru.repository.BadgeRepository;
import odoru.repository.CoursRepository;
import odoru.utilities.BadgeDejaAssocieException;
import odoru.utilities.PresenceDejaEnregistreeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BadgeServiceTest {

    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    private PresenceRepository presenceRepository;

    @Mock
    private CoursRepository coursRepository;

    @InjectMocks
    private BadgeService badgeService;

    private Badge badge;
    private Cours cours;

    @BeforeEach
    void setUp() {
        badge = new Badge();
        badge.setNumeroBadge("BADGE001");
        badge.setMembreId("membre1");

        cours = new Cours();
        cours.setTitre("Danse niveau 5");
    }

    @Test
    void assignerBadge_succes() {
        when(badgeRepository.existsByNumeroBadge("BADGE001")).thenReturn(false);
        when(badgeRepository.save(any(Badge.class))).thenReturn(badge);

        Badge result = badgeService.assignerBadge("BADGE001", "membre1");

        assertNotNull(result);
        verify(badgeRepository, times(1)).save(any(Badge.class));
    }

    @Test
    void assignerBadge_dejaAssigne() {
        when(badgeRepository.existsByNumeroBadge("BADGE001")).thenReturn(true);

        BadgeDejaAssocieException ex = assertThrows(BadgeDejaAssocieException.class,
                () -> badgeService.assignerBadge("BADGE001", "membre1"));

        assertEquals("Ce badge est déjà associé à un membre", ex.getMessage());
        verify(badgeRepository, never()).save(any());
    }

    @Test
    void scanner_succes() {
        when(badgeRepository.findByNumeroBadge("BADGE001")).thenReturn(Optional.of(badge));
        when(coursRepository.findById("cours1")).thenReturn(Optional.of(cours));
        when(presenceRepository.existsByMembreIdAndCoursId("membre1", "cours1")).thenReturn(false);
        when(presenceRepository.save(any(Presence.class))).thenAnswer(i -> i.getArgument(0));

        Presence result = badgeService.scanner("BADGE001", "cours1");

        assertNotNull(result);
        assertEquals("membre1", result.getMembreId());
        assertEquals("cours1", result.getCoursId());
    }

    @Test
    void scanner_presenceDejaEnregistree() {
        when(badgeRepository.findByNumeroBadge("BADGE001")).thenReturn(Optional.of(badge));
        when(coursRepository.findById("cours1")).thenReturn(Optional.of(cours));
        when(presenceRepository.existsByMembreIdAndCoursId("membre1", "cours1")).thenReturn(true);

        PresenceDejaEnregistreeException ex = assertThrows(PresenceDejaEnregistreeException.class,
                () -> badgeService.scanner("BADGE001", "cours1"));

        assertEquals("Présence déjà enregistrée", ex.getMessage());
        verify(presenceRepository, never()).save(any());
    }
}