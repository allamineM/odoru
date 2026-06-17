package odoru.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Statistiques (Président)", description = "API d'extraction des indicateurs et statistiques pour le pilotage du club")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    @Operation(summary = "Résumé global des cours", description = "Retourne le nombre total de cours et la moyenne d'élèves présents.")
    @GetMapping("/cours/resume")
    public ResponseEntity<Map<String, Object>> getResumesCours() {
        return ResponseEntity.ok(statistiqueService.getNombreCoursEtMoyennePresences());
    }

    @Operation(summary = "Présences à un cours", description = "Retourne le nombre et la liste nominative des élèves présents à un cours.")
    @GetMapping("/cours/{coursId}/eleves")
    public ResponseEntity<Map<String, Object>> getElevesParCours(@PathVariable String coursId) {
        return ResponseEntity.ok(statistiqueService.getElevesParCours(coursId));
    }

    @Operation(summary = "Historique des cours d'un membre", description = "Liste des cours avec indication de présence ou d'absence. Filtrable par dates.")
    @GetMapping("/membres/{membreId}/cours")
    public ResponseEntity<List<Map<String, Object>>> getCoursMembreAvecPresences(
            @PathVariable String membreId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(statistiqueService.getCoursMembreAvecPresences(membreId, debut, fin));
    }

    @Operation(summary = "Décompte des compétitions", description = "Retourne le nombre total de compétitions organisées pour un niveau donné.")
    @GetMapping("/competitions/niveau/{niveau}")
    public ResponseEntity<Map<String, Object>> getNombreCompetitionsParNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(statistiqueService.getNombreCompetitionsParNiveau(niveau));
    }

    @Operation(summary = "Palmarès d'un membre", description = "Liste des compétitions avec les résultats obtenus. Filtrable par dates.")
    @GetMapping("/membres/{membreId}/competitions")
    public ResponseEntity<List<Map<String, Object>>> getCompetitionsMembreAvecResultats(
            @PathVariable String membreId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(statistiqueService.getCompetitionsMembreAvecResultats(membreId, debut, fin));
    }
}