package odoru.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import odoru.entities.Utilisateur;
import odoru.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@Tag(name = "Utilisateurs", description = "API de gestion des membres, enseignants et administrateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Operation(summary = "S'inscrire", description = "Crée un compte. Attribue automatiquement le rôle MEMBER.")
    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur) {
        return ResponseEntity.ok(utilisateurService.inscription(utilisateur));
    }

    @Operation(summary = "Se connecter", description = "Vérifie les identifiants et retourne le profil de l'utilisateur.")
    @PostMapping("/login")
    public ResponseEntity<Utilisateur> login(@RequestParam String nomUtilisateur,
                                             @RequestParam String motDePasse) {
        return ResponseEntity.ok(utilisateurService.login(nomUtilisateur, motDePasse));
    }

    @Operation(summary = "Lister les utilisateurs")
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }

    @Operation(summary = "Détails d'un utilisateur")
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable String id) {
        return utilisateurService.getUtilisateurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Mettre à jour le niveau", description = "Modifie le niveau d'expertise (1 à 5) d'un membre. Action réservée au Secrétaire métier.")
    @PutMapping("/{id}/expertise")
    public ResponseEntity<Utilisateur> updateExpertise(@PathVariable String id,
                                                       @RequestParam int niveau) {
        return ResponseEntity.ok(utilisateurService.updateNiveauExpertise(id, niveau));
    }

    @Operation(summary = "Ajouter un rôle", description = "Ajoute un rôle spécifique (ex: TEACHER, SECRETARY) à un membre existant.")
    @PutMapping("/{id}/role")
    public ResponseEntity<Utilisateur> addRole(@PathVariable String id,
                                               @RequestParam Utilisateur.Role role) {
        return ResponseEntity.ok(utilisateurService.addRole(id, role));
    }

    @Operation(summary = "Supprimer un utilisateur")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable String id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}