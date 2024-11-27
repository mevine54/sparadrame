package fr.afpa.pompey.cda22045.models;

// Classe Adresse
public class Adresse {
    private Integer adrId;
    private String rue;
    private String codePostal;
    private String ville;

    public Adresse() {}

    public Adresse(Integer adrId, String rue, String codePostal, String ville) {
        setAdrId(adrId);
        setRue(rue);
        setCodePostal(codePostal);
        setVille(ville);
    }

    public Integer getAdrId() {
        return adrId;
    }

    public void setAdrId(Integer adrId) {
        if (adrId != null && adrId < 0) {
            throw new IllegalArgumentException("L'id de l'adresse ne peut pas être négatif.");
        }
        this.adrId = adrId;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
//        if (rue == null || rue.isBlank()) {
//            throw new IllegalArgumentException("La rue ne peut pas être vide.");
//        }
        this.rue = rue; // adresse peut être null
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
//        if (codePostal == null || !codePostal.matches("\\d{5}")) {
//            throw new IllegalArgumentException("Le code postal doit contenir exactement 5 chiffres.");
//        }
        this.codePostal = codePostal;// adresse peut être null
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
//        if (ville == null || ville.isBlank()) {
//            throw new IllegalArgumentException("La ville ne peut pas être vide.");
//        }
        this.ville = ville;// adresse peut être null
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "adrId=" + adrId +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
