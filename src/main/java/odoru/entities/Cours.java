package odoru.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "cours")
@Schema(description = "Entité représentant un créneau de cours de danse rythmique")
public class Cours {

    @Id
    @Schema(description = "Identifiant unique du cours généré par MongoDB", example = "60b9ce...c1")
    private String id;

    @Schema(description = "Titre descriptif du cours", example = "Initiation au rythme")
    private String titre;

    @Schema(description = "Niveau requis pour participer au cours (1 à 5)", example = "2")
    private int niveauCible;

    @Schema(description = "Date et heure prévues pour le cours", example = "2026-06-25T18:00:00")
    private LocalDateTime dateHeure;

    @Schema(description = "Identifiant de l'enseignant dispensant le cours", example = "60b9ce...e1")
    private String enseignantId;

    @Schema(description = "Lieu où se déroulera le cours", example = "Salle A")
    private String lieu;

    @Schema(description = "Durée du cours en minutes", example = "60")
    private int dureeMinutes;

    @Schema(description = "Date de saisie du cours dans le système")
    private LocalDateTime creeLe;

    public Cours() {
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
}