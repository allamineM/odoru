package odoru.web;

import odoru.domain.Competition;
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
                                                         @RequestParam String teacherId) {
        return ResponseEntity.ok(competitionService.createCompetition(competition, teacherId));
    }

    @PutMapping("/{id}/result")
    public ResponseEntity<Competition> addResult(@PathVariable String id,
                                                 @RequestParam String memberId,
                                                 @RequestParam double score) {
        return ResponseEntity.ok(competitionService.addResult(id, memberId, score));
    }

    @GetMapping
    public ResponseEntity<List<Competition>> getAllCompetitions() {
        return ResponseEntity.ok(competitionService.getAllCompetitions());
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<Competition>> getByLevel(@PathVariable int level) {
        return ResponseEntity.ok(competitionService.getCompetitionsByLevel(level));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Competition>> getByMember(@PathVariable String memberId) {
        return ResponseEntity.ok(competitionService.getCompetitionsByMember(memberId));
    }
}
