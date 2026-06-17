package odoru.service;

import odoru.entities.Presence;
import odoru.entities.Badge;
import odoru.entities.Cours;
import odoru.repository.AttendanceRepository;
import odoru.repository.BadgeRepository;
import odoru.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Badge assignerBadge(String numeroBadge, String membreId) {
        if (badgeRepository.existsByNumeroBadge(numeroBadge)) {
            throw new RuntimeException("Ce badge est déjà associé à un membre");
        }
        Badge badge = new Badge();
        badge.setNumeroBadge(numeroBadge);
        badge.setMembreId(membreId);
        return badgeRepository.save(badge);
    }

    public void retirerBadge(String numeroBadge) {
        Badge badge = badgeRepository.findByNumeroBadge(numeroBadge)
                .orElseThrow(() -> new RuntimeException("Badge introuvable"));
        badgeRepository.delete(badge);
    }

    public Presence scanner(String numeroBadge, String coursId) {
        Badge badge = badgeRepository.findByNumeroBadge(numeroBadge)
                .orElseThrow(() -> new RuntimeException("Badge inconnu"));

        courseRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        if (attendanceRepository.existsByMembreIdAndCoursId(badge.getMembreId(), coursId)) {
            throw new RuntimeException("Présence déjà enregistrée");
        }

        Presence presence = new Presence();
        presence.setMembreId(badge.getMembreId());
        presence.setCoursId(coursId);
        return attendanceRepository.save(presence);
    }

    public List<Cours> getCourseSuivisParMembre(String membreId) {
        return attendanceRepository.findByMembreId(membreId).stream()
                .map(a -> courseRepository.findById(a.getCoursId()).orElse(null))
                .filter(c -> c != null)
                .toList();
    }

    public List<Presence> getPresencesParCours(String coursId) {
        return attendanceRepository.findByCoursId(coursId);
    }
}
