package odoru.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "competitions")
@Schema(description = "Entité représentant une compétition organisée par un enseignant")
public class Competition {

    @Id
    @Schema(description = "Identifiant unique de la compétition", example = "60b9ce...comp1")
    private String id;

    @Schema(description = "Titre de la compétition", example = "Tournoi d'été")
    private String titre;

    @Schema(description = "Niveau visé par la compétition (1 à 5)", example = "4")
    private int niveauCible;

    @Schema(description = "Date et heure de la compétition", example = "2026-07-01T14:00:00")
    private LocalDateTime dateHeure;

    @Schema(description = "Identifiant de l'enseignant organisateur", example = "60b9ce...e1")
    private String enseignantId;

    @Schema(description = "Lieu de l'évènement", example = "Gymnase principal")
    private String lieu;

    @Schema(description = "Durée en minutes de la compétition", example = "120")
    private int dureeMinutes;

    @Schema(description = "Date de saisie dans le système")
    private LocalDateTime creeLe;

    @Schema(description = "Résultats des élèves (Clé : membreId, Valeur : note sur 10 au dixième près)")
    private Map<String, Double> resultats = new HashMap<>();

    public Competition() {
        this.creeLe = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public int getNiveauCible() { return niveauCible; }
    public void setNiveauCible(int niveauCible) { this.niveauCible = niveauCible; }

    public LocalDateTime getDateHeure() { return dateHeure; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }

    public String getEnseignantId() { return enseignantId; }
    public void setEnseignantId(String enseignantId) { this.enseignantId = enseignantId; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public int getDureeMinutes() { return dureeMinutes; }
    public void setDureeMinutes(int dureeMinutes) { this.dureeMinutes = dureeMinutes; }

    public LocalDateTime getCreeLe() { return creeLe; }

    public Map<String, Double> getResultats() { return resultats; }
    public void setResultats(Map<String, Double> resultats) { this.resultats = resultats; }
}