package odoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String nom;
    private String prenom;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String nomUtilisateur;

    private String motDePasse;
    private Address adresse;
    private int niveauExpertise; // 1 à 5
    private Set<Role> roles = new HashSet<>();

    public enum Role {
        MEMBER, SECRETARY, TEACHER, PRESIDENT
    }

    public User() {}

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

    public Address getAdresse() { return adresse; }
    public void setAdresse(Address adresse) { this.adresse = adresse; }

    public int getNiveauExpertise() { return niveauExpertise; }
    public void setNiveauExpertise(int niveauExpertise) { this.niveauExpertise = niveauExpertise; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
