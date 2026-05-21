package odoru.web;

import odoru.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    // Nombre de cours et moyenne d'élèves présents
    @GetMapping("/cours/resume")
    public ResponseEntity<Map<String, Object>> getResumesCours() {
        return ResponseEntity.ok(statistiqueService.getNombreCoursEtMoyennePresences());
    }

    // Élèves présents à un cours donné
    @GetMapping("/cours/{coursId}/eleves")
    public ResponseEntity<Map<String, Object>> getElevesParCours(@PathVariable String coursId) {
        return ResponseEntity.ok(statistiqueService.getElevesParCours(coursId));
    }

    // Cours d'un membre avec présences/absences (filtre optionnel par période)
    @GetMapping("/membres/{membreId}/cours")
    public ResponseEntity<List<Map<String, Object>>> getCoursMembreAvecPresences(
            @PathVariable String membreId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(statistiqueService.getCoursMembreAvecPresences(membreId, debut, fin));
    }

    // Nombre de compétitions par niveau
    @GetMapping("/competitions/niveau/{niveau}")
    public ResponseEntity<Map<String, Object>> getNombreCompetitionsParNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(statistiqueService.getNombreCompetitionsParNiveau(niveau));
    }

    // Compétitions d'un membre avec résultats (filtre optionnel par période)
    @GetMapping("/membres/{membreId}/competitions")
    public ResponseEntity<List<Map<String, Object>>> getCompetitionsMembreAvecResultats(
            @PathVariable String membreId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(statistiqueService.getCompetitionsMembreAvecResultats(membreId, debut, fin));
    }
}
