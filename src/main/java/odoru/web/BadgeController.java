package odoru.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Badges et Présences", description = "API pour l'attribution des badges et le pointage (scan) lors des cours")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    @Operation(summary = "Assigner un badge", description = "Associe un numéro de badge physique à un membre du club.")
    @PostMapping("/assigner")
    public ResponseEntity<Badge> assignerBadge(@RequestParam String numeroBadge,
                                               @RequestParam String membreId) {
        return ResponseEntity.ok(badgeService.assignerBadge(numeroBadge, membreId));
    }

    @Operation(summary = "Retirer un badge", description = "Dissocie un badge. À utiliser en cas de perte du boîtier.")
    @DeleteMapping("/{numeroBadge}")
    public ResponseEntity<Void> retirerBadge(@PathVariable String numeroBadge) {
        badgeService.retirerBadge(numeroBadge);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Scanner un badge (Boîtier)", description = "Simule le passage du badge sur le boîtier pour valider la présence à un cours.")
    @PostMapping("/scanner")
    public ResponseEntity<Presence> scanner(@RequestParam String numeroBadge,
                                            @RequestParam String coursId) {
        return ResponseEntity.ok(badgeService.scanner(numeroBadge, coursId));
    }

    @Operation(summary = "Cours suivis", description = "Retourne la liste des cours auxquels l'élève a assisté.")
    @GetMapping("/membre/{membreId}/cours")
    public ResponseEntity<List<Cours>> getCoursSuivis(@PathVariable String membreId) {
        return ResponseEntity.ok(badgeService.getCoursSuivisParMembre(membreId));
    }

    @Operation(summary = "Liste des présences", description = "Retourne l'historique des présences pour un cours spécifique.")
    @GetMapping("/cours/{coursId}/presences")
    public ResponseEntity<List<Presence>> getPresences(@PathVariable String coursId) {
        return ResponseEntity.ok(badgeService.getPresencesParCours(coursId));
    }
}