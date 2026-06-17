package odoru.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "utilisateurs")
@Schema(description = "Entité représentant un utilisateur de la plateforme CallMe (membre, secrétaire, enseignant ou président)")
public class Utilisateur {

    @Id
    @Schema(description = "Identifiant unique généré par MongoDB", example = "60b9ce42d9...a3")
    private String id;

    @Schema(description = "Nom de famille de l'utilisateur", example = "Sadfi")
    private String nom;

    @Schema(description = "Prénom de l'utilisateur", example = "Youssef")
    private String prenom;

    @Indexed(unique = true)
    @Schema(description = "Adresse email unique de contact", example = "youssef@email.com")
    private String email;

    @Indexed(unique = true)
    @Schema(description = "Identifiant de connexion unique", example = "ysadfi26")
    private String nomUtilisateur;

    @Schema(description = "Mot de passe de connexion", example = "secret123")
    private String motDePasse;

    @Schema(description = "Adresse de l'utilisateur")
    private Adresse adresse; // Référence mise à jour vers la classe Adresse

    @Schema(description = "Niveau d'expertise en danse rythmique (de 1 à 5)", example = "3")
    private int niveauExpertise;

    @Schema(description = "Rôles attribués à l'utilisateur dans le système", example = "[\"MEMBER\", \"TEACHER\"]")
    private Set<Role> roles = new HashSet<>();

    public enum Role {
        MEMBER, SECRETARY, TEACHER, PRESIDENT
    }

    public Utilisateur() {}

    public String getId() { return id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNomUtilisateur() { return nomUtilisateur; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public Adresse getAdresse() { return adresse; }
    public void setAdresse(Adresse adresse) { this.adresse = adresse; }

    public int getNiveauExpertise() { return niveauExpertise; }
    public void setNiveauExpertise(int niveauExpertise) { this.niveauExpertise = niveauExpertise; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}