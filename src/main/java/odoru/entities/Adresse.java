package odoru.entities;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entité représentant l'adresse de résidence d'un utilisateur")
public class Adresse {

    @Schema(description = "Ville de résidence", example = "Toulouse")
    private String ville;

    @Schema(description = "Pays de résidence", example = "France")
    private String pays;

    public Adresse() {}

    public Adresse(String ville, String pays) {
        this.ville = ville;
        this.pays = pays;
    }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }
}