package fr.afpa.pompey.cda22045.models;

// Classe Adresse
public class Adresse {
    private Integer adrId;
    private String rue;
    private String codePostal;
    private String ville;

    // Constructeur de la classe Adresse
    public Adresse(Integer adrId, String rue, String codePostal, String ville) {
        setAdrId(adrId);
        setRue(rue);
        setCodePostal(codePostal);
        setVille(ville);
    }

    // Getters et setters
    public int getAdrId() {
        return adrId;
    }

    public void setAdrId(Integer adrId) {
        this.adrId = adrId;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return rue + ", " + codePostal + " " + ville;
    }
}
