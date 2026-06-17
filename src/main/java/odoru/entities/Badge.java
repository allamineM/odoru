package odoru.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "badges")
@Schema(description = "Entité représentant le badge physique distribué à un élève")
public class Badge {

    @Id
    @Schema(description = "Identifiant technique généré par MongoDB", example = "60b9ce...b1")
    private String id;

    @Schema(description = "Numéro aléatoire inscrit sur le boîtier physique", example = "841928374")
    private String numeroBadge;

    @Schema(description = "Identifiant de l'élève associé au badge", example = "60b9ce...u1")
    private String membreId;

    public Badge() {}

    public String getId() { return id; }

    public String getNumeroBadge() { return numeroBadge; }
    public void setNumeroBadge(String numeroBadge) { this.numeroBadge = numeroBadge; }

    public String getMembreId() { return membreId; }
    public void setMembreId(String membreId) { this.membreId = membreId; }
}