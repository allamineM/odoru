package odoru.web;

import odoru.domain.Attendance;
import odoru.domain.Badge;
import odoru.domain.Course;
import odoru.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    // Secrétaire associe un badge
    @PostMapping("/assign")
    public ResponseEntity<Badge> assignBadge(@RequestParam String badgeNumber,
                                             @RequestParam String memberId) {
        return ResponseEntity.ok(badgeService.assignBadge(badgeNumber, memberId));
    }

    // Secrétaire dissocie un badge
    @DeleteMapping("/{badgeNumber}")
    public ResponseEntity<Void> removeBadge(@PathVariable String badgeNumber) {
        badgeService.removeBadge(badgeNumber);
        return ResponseEntity.noContent().build();
    }

    // Simulation boîtier : scan du badge lors d'un cours
    @PostMapping("/scan")
    public ResponseEntity<Attendance> scan(@RequestParam String badgeNumber,
                                           @RequestParam String courseId) {
        return ResponseEntity.ok(badgeService.scan(badgeNumber, courseId));
    }

    // Cours suivis par un membre
    @GetMapping("/member/{memberId}/courses")
    public ResponseEntity<List<Course>> getAttendedCourses(@PathVariable String memberId) {
        return ResponseEntity.ok(badgeService.getAttendedCourses(memberId));
    }

    // Présences à un cours
    @GetMapping("/course/{courseId}/attendances")
    public ResponseEntity<List<Attendance>> getAttendancesByCourse(@PathVariable String courseId) {
        return ResponseEntity.ok(badgeService.getAttendancesByCourse(courseId));
    }
}
