package odoru.web;

import odoru.entities.Presence;
import odoru.entities.Badge;
import odoru.entities.Cours;
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

    @PostMapping("/assigner")
    public ResponseEntity<Badge> assignerBadge(@RequestParam String numeroBadge,
                                               @RequestParam String membreId) {
        return ResponseEntity.ok(badgeService.assignerBadge(numeroBadge, membreId));
    }

    @DeleteMapping("/{numeroBadge}")
    public ResponseEntity<Void> retirerBadge(@PathVariable String numeroBadge) {
        badgeService.retirerBadge(numeroBadge);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/scanner")
    public ResponseEntity<Presence> scanner(@RequestParam String numeroBadge,
                                            @RequestParam String coursId) {
        return ResponseEntity.ok(badgeService.scanner(numeroBadge, coursId));
    }

    @GetMapping("/membre/{membreId}/cours")
    public ResponseEntity<List<Cours>> getCourseSuivis(@PathVariable String membreId) {
        return ResponseEntity.ok(badgeService.getCourseSuivisParMembre(membreId));
    }

    @GetMapping("/cours/{coursId}/presences")
    public ResponseEntity<List<Presence>> getPresences(@PathVariable String coursId) {
        return ResponseEntity.ok(badgeService.getPresencesParCours(coursId));
    }
}
