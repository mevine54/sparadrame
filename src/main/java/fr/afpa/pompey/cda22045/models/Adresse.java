package fr.afpa.pompey.cda22045.models;

public class Adresse {
    private Integer adrId;
    private String adrRue;
    private String adrCodePostal;
    private String adrVille;

    // Constructeur par défaut
    public Adresse() {}

    // Constructeur avec paramètres
    public Adresse(Integer adrId, String adrRue, String adrCodePostal, String adrVille) {
        setAdrId(adrId);
        setAdrRue(adrRue);
        setAdrCodePostal(adrCodePostal);
        setAdrVille(adrVille);
    }

    // Getters et setters
    public Integer getAdrId() {
        return adrId;
    }

    public void setAdrId(Integer adrId) {
        if (adrId != null && adrId < 0) {
            throw new IllegalArgumentException("L'id adresse ne peut pas être négatif.");
        }
        this.adrId = adrId;
    }

    public String getAdrRue() {
        return adrRue;
    }

    public void setAdrRue(String adrRue) {
        if (adrRue == null || adrRue.isBlank()) {
            throw new IllegalArgumentException("La rue ne peut pas être vide.");
        }
        this.adrRue = adrRue;
    }

    public String getAdrCodePostal() {
        return adrCodePostal;
    }

    public void setAdrCodePostal(String adrCodePostal) {
        if (adrCodePostal == null || adrCodePostal.isBlank()) {
            throw new IllegalArgumentException("Le code postal ne peut pas être vide.");
        }
        this.adrCodePostal = adrCodePostal;
    }

    public String getAdrVille() {
        return adrVille;
    }

    public void setAdrVille(String adrVille) {
        if (adrVille == null || adrVille.isBlank()) {
            throw new IllegalArgumentException("La ville ne peut pas être vide.");
        }
        this.adrVille = adrVille;
    }

    @Override
    public String toString() {
        return adrRue + ", " + adrCodePostal + " " + adrVille;
    }
}
