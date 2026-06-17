package odoru.web;

import odoru.entities.Utilisateur;
import odoru.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(userService.inscription(utilisateur));
    }

    @PostMapping("/login")
    public ResponseEntity<Utilisateur> login(@RequestParam String nomUtilisateur,
                                             @RequestParam String motDePasse) {
        return ResponseEntity.ok(userService.login(nomUtilisateur, motDePasse));
    }

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/expertise")
    public ResponseEntity<Utilisateur> updateExpertise(@PathVariable String id,
                                                       @RequestParam int niveau) {
        return ResponseEntity.ok(userService.updateNiveauExpertise(id, niveau));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<Utilisateur> addRole(@PathVariable String id,
                                               @RequestParam Utilisateur.Role role) {
        return ResponseEntity.ok(userService.addRole(id, role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
