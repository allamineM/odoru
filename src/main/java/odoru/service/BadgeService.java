package odoru.service;

import odoru.domain.Attendance;
import odoru.domain.Badge;
import odoru.domain.Course;
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

    // Secrétaire associe un badge à un membre
    public Badge assignBadge(String badgeNumber, String memberId) {
        if (badgeRepository.existsByBadgeNumber(badgeNumber)) {
            throw new RuntimeException("Ce badge est déjà associé à un membre");
        }
        Badge badge = new Badge();
        badge.setBadgeNumber(badgeNumber);
        badge.setMemberId(memberId);
        return badgeRepository.save(badge);
    }

    // Secrétaire dissocie un badge
    public void removeBadge(String badgeNumber) {
        Badge badge = badgeRepository.findByBadgeNumber(badgeNumber)
                .orElseThrow(() -> new RuntimeException("Badge introuvable"));
        badgeRepository.delete(badge);
    }

    // Simulation du boîtier : badge scanné lors d'un cours
    public Attendance scan(String badgeNumber, String courseId) {
        Badge badge = badgeRepository.findByBadgeNumber(badgeNumber)
                .orElseThrow(() -> new RuntimeException("Badge inconnu"));

        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        if (attendanceRepository.existsByMemberIdAndCourseId(badge.getMemberId(), courseId)) {
            throw new RuntimeException("Présence déjà enregistrée");
        }

        Attendance attendance = new Attendance();
        attendance.setMemberId(badge.getMemberId());
        attendance.setCourseId(courseId);
        return attendanceRepository.save(attendance);
    }

    // Cours suivis par un membre
    public List<Course> getAttendedCourses(String memberId) {
        return attendanceRepository.findByMemberId(memberId).stream()
                .map(a -> courseRepository.findById(a.getCourseId()).orElse(null))
                .filter(c -> c != null)
                .toList();
    }

    // Liste des membres présents à un cours
    public List<Attendance> getAttendancesByCourse(String courseId) {
        return attendanceRepository.findByCourseId(courseId);
    }
}
