package odoru.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import odoru.entities.Cours;
import odoru.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
@Tag(name = "Cours", description = "API de planification et consultation des créneaux de cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @Operation(summary = "Planifier un cours", description = "Crée un cours. La date doit être supérieure de 7 jours par rapport à aujourd'hui.")
    @PostMapping
    public ResponseEntity<Cours> createCours(@RequestBody Cours cours,
                                             @RequestParam String enseignantId) {
        return ResponseEntity.ok(coursService.createCours(cours, enseignantId));
    }

    @Operation(summary = "Lister tous les cours")
    @GetMapping
    public ResponseEntity<List<Cours>> getAllCours() {
        return ResponseEntity.ok(coursService.getAllCours());
    }

    @Operation(summary = "Filtrer par niveau cible")
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Cours>> getCoursByNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(coursService.getCoursByNiveau(niveau));
    }

    @Operation(summary = "Cours d'un enseignant", description = "Récupère les créneaux dispensés par un enseignant spécifique.")
    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Cours>> getCoursByEnseignant(@PathVariable String enseignantId) {
        return ResponseEntity.ok(coursService.getCoursByEnseignant(enseignantId));
    }

    @Operation(summary = "Cours d'un membre", description = "Récupère les créneaux correspondant au niveau d'expertise du membre (inscription de facto).")
    @GetMapping("/membre/{membreId}")
    public ResponseEntity<List<Cours>> getCoursByMembre(@PathVariable String membreId) {
        return ResponseEntity.ok(coursService.getCoursByMembre(membreId));
    }
}