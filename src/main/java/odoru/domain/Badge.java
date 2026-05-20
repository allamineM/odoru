package odoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "badges")
public class Badge {

    @Id
    private String id;

    private String numeroBadge;
    private String membreId;

    public Badge() {}

    public String getId() { return id; }

    public String getNumeroBadge() { return numeroBadge; }
    public void setNumeroBadge(String numeroBadge) { this.numeroBadge = numeroBadge; }

    public String getMembreId() { return membreId; }
    public void setMembreId(String membreId) { this.membreId = membreId; }
}
