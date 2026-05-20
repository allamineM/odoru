package odoru.web;

import odoru.domain.Course;
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
    public ResponseEntity<Course> createCourse(@RequestBody Course course,
                                               @RequestParam String enseignantId) {
        return ResponseEntity.ok(courseService.createCourse(course, enseignantId));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Course>> getCoursesByNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(courseService.getCoursesByNiveau(niveau));
    }

    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Course>> getCoursesByEnseignant(@PathVariable String enseignantId) {
        return ResponseEntity.ok(courseService.getCoursesByEnseignant(enseignantId));
    }

    @GetMapping("/membre/{membreId}")
    public ResponseEntity<List<Course>> getCoursesByMembre(@PathVariable String membreId) {
        return ResponseEntity.ok(courseService.getCoursesByMembre(membreId));
    }
}
