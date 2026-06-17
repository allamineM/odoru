package odoru.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "presences")
@Schema(description = "Entité représentant la présence enregistrée d'un élève à un cours")
public class Presence {

    @Id
    @Schema(description = "Identifiant unique de la présence", example = "60b9ce...p1")
    private String id;

    @Schema(description = "Identifiant du membre ayant badgé", example = "60b9ce...u1")
    private String membreId;

    @Schema(description = "Identifiant du cours concerné par le badge", example = "60b9ce...c1")
    private String coursId;

    @Schema(description = "Date et heure exactes du scan sur le boîtier", example = "2026-06-25T17:55:00")
    private LocalDateTime scanneLe;

    public Presence() {
        this.scanneLe = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getMembreId() { return membreId; }
    public void setMembreId(String membreId) { this.membreId = membreId; }

    public String getCoursId() { return coursId; }
    public void setCoursId(String coursId) { this.coursId = coursId; }

    public LocalDateTime getScanneLe() { return scanneLe; }
}