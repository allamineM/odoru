package odoru.domain;

public class Address {

    private String ville;
    private String pays;

    public Address() {}

    public Address(String ville, String pays) {
        this.ville = ville;
        this.pays = pays;
    }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }
}
