package odoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    private String titre;
    private int niveauCible;
    private LocalDateTime dateHeure;
    private String enseignantId;
    private String lieu;
    private int dureeMinutes;
    private LocalDateTime creeLe;

    public Course() {
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
