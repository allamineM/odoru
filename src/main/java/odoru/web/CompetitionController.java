package odoru.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import odoru.entities.Competition;
import odoru.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competitions")
@Tag(name = "Compétitions", description = "API de planification et de notation des compétitions de danse")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @Operation(summary = "Créer une compétition", description = "L'enseignant doit avoir un niveau supérieur ou égal au niveau cible.")
    @PostMapping
    public ResponseEntity<Competition> createCompetition(@RequestBody Competition competition,
                                                         @RequestParam String enseignantId) {
        return ResponseEntity.ok(competitionService.createCompetition(competition, enseignantId));
    }

    @Operation(summary = "Ajouter un résultat", description = "Enregistre la note (sur 10, au dixième près) d'un élève pour une compétition.")
    @PutMapping("/{id}/resultat")
    public ResponseEntity<Competition> addResultat(@PathVariable String id,
                                                   @RequestParam String membreId,
                                                   @RequestParam double note) {
        return ResponseEntity.ok(competitionService.addResultat(id, membreId, note));
    }

    @Operation(summary = "Lister toutes les compétitions")
    @GetMapping
    public ResponseEntity<List<Competition>> getAllCompetitions() {
        return ResponseEntity.ok(competitionService.getAllCompetitions());
    }

    @Operation(summary = "Filtrer par niveau", description = "Récupère les compétitions correspondant à un niveau cible spécifique.")
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Competition>> getByNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(competitionService.getCompetitionsByNiveau(niveau));
    }

    @Operation(summary = "Compétitions d'un élève", description = "Récupère les compétitions auxquelles l'élève a participé (ayant un résultat).")
    @GetMapping("/membre/{membreId}")
    public ResponseEntity<List<Competition>> getByMembre(@PathVariable String membreId) {
        return ResponseEntity.ok(competitionService.getCompetitionsByMembre(membreId));
    }

    @Operation(summary = "Compétitions d'un enseignant", description = "Récupère les compétitions organisées par un enseignant spécifique.")
    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Competition>> getByEnseignant(@PathVariable String enseignantId) {
        return ResponseEntity.ok(competitionService.getCompetitionsByEnseignant(enseignantId));
    }
}