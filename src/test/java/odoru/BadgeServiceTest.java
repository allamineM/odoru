package odoru;

import odoru.domain.Attendance;
import odoru.domain.Badge;
import odoru.domain.Course;
import odoru.repository.AttendanceRepository;
import odoru.repository.BadgeRepository;
import odoru.repository.CourseRepository;
import odoru.service.BadgeService;
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
    private AttendanceRepository attendanceRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private BadgeService badgeService;

    private Badge badge;
    private Course cours;

    @BeforeEach
    void setUp() {
        badge = new Badge();
        badge.setNumeroBadge("BADGE001");
        badge.setMembreId("membre1");

        cours = new Course();
        cours.setTitre("Danse niveau 5");
    }

    @Test
    void assignerBadge_succes() {
        when(badgeRepository.existsByNumeroBadge("BADGE001")).thenReturn(false);
        when(badgeRepository.save(any(Badge.class))).thenReturn(badge);

        Badge result = badgeService.assignerBadge("BADGE001", "membre1");

        assertNotNull(result);
        assertEquals("BADGE001", result.getNumeroBadge());
        assertEquals("membre1", result.getMembreId());
        verify(badgeRepository, times(1)).save(any(Badge.class));
    }

    @Test
    void assignerBadge_dejaAssigne() {
        when(badgeRepository.existsByNumeroBadge("BADGE001")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> badgeService.assignerBadge("BADGE001", "membre1"));

        assertEquals("Ce badge est déjà associé à un membre", ex.getMessage());
        verify(badgeRepository, never()).save(any());
    }

    @Test
    void scanner_succes() {
        when(badgeRepository.findByNumeroBadge("BADGE001")).thenReturn(Optional.of(badge));
        when(courseRepository.findById("cours1")).thenReturn(Optional.of(cours));
        when(attendanceRepository.existsByMembreIdAndCoursId("membre1", "cours1")).thenReturn(false);
        when(attendanceRepository.save(any(Attendance.class))).thenAnswer(i -> i.getArgument(0));

        Attendance result = badgeService.scanner("BADGE001", "cours1");

        assertNotNull(result);
        assertEquals("membre1", result.getMembreId());
        assertEquals("cours1", result.getCoursId());
    }

    @Test
    void scanner_presenceDejaEnregistree() {
        when(badgeRepository.findByNumeroBadge("BADGE001")).thenReturn(Optional.of(badge));
        when(courseRepository.findById("cours1")).thenReturn(Optional.of(cours));
        when(attendanceRepository.existsByMembreIdAndCoursId("membre1", "cours1")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> badgeService.scanner("BADGE001", "cours1"));

        assertEquals("Présence déjà enregistrée", ex.getMessage());
        verify(attendanceRepository, never()).save(any());
    }
}
