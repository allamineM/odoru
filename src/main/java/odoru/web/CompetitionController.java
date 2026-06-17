package odoru.web;

import odoru.entities.Competition;
import odoru.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @PostMapping
    public ResponseEntity<Competition> createCompetition(@RequestBody Competition competition,
                                                         @RequestParam String enseignantId) {
        return ResponseEntity.ok(competitionService.createCompetition(competition, enseignantId));
    }

    @PutMapping("/{id}/resultat")
    public ResponseEntity<Competition> addResultat(@PathVariable String id,
                                                   @RequestParam String membreId,
                                                   @RequestParam double note) {
        return ResponseEntity.ok(competitionService.addResultat(id, membreId, note));
    }

    @GetMapping
    public ResponseEntity<List<Competition>> getAllCompetitions() {
        return ResponseEntity.ok(competitionService.getAllCompetitions());
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Competition>> getByNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(competitionService.getCompetitionsByNiveau(niveau));
    }

    @GetMapping("/membre/{membreId}")
    public ResponseEntity<List<Competition>> getByMembre(@PathVariable String membreId) {
        return ResponseEntity.ok(competitionService.getCompetitionsByMembre(membreId));
    }
}
