package odoru.web;

import odoru.entities.Cours;
import odoru.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Cours> createCourse(@RequestBody Cours cours,
                                              @RequestParam String enseignantId) {
        return ResponseEntity.ok(courseService.createCourse(cours, enseignantId));
    }

    @GetMapping
    public ResponseEntity<List<Cours>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Cours>> getCoursesByNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(courseService.getCoursesByNiveau(niveau));
    }

    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Cours>> getCoursesByEnseignant(@PathVariable String enseignantId) {
        return ResponseEntity.ok(courseService.getCoursesByEnseignant(enseignantId));
    }

    @GetMapping("/membre/{membreId}")
    public ResponseEntity<List<Cours>> getCoursesByMembre(@PathVariable String membreId) {
        return ResponseEntity.ok(courseService.getCoursesByMembre(membreId));
    }
}