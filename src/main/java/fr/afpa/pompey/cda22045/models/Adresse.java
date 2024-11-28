package fr.afpa.pompey.cda22045.models;

// Classe Adresse
public class Adresse {
    private Integer adrId;
    private String adrRue;
    private String adrCodePostal;
    private String adrVille;

    public Adresse() {}

    public Adresse(Integer adrId, String adrRue, String adrCodePostal, String adrVille) {
        setAdrId(adrId);
        setAdrRue(adrRue);
        setAdrCodePostal(adrCodePostal);
        setAdrVille(adrVille);
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

    public String getAdrRue() {
        return adrRue;
    }

    public void setAdrRue(String rue) {
//        if (adrRue == null || adrRue.isBlank()) {
//            throw new IllegalArgumentException("La rue ne peut pas être vide.");
//        }
        this.adrRue = adrRue; // adresse peut être null
    }

    public String getAdrCodePostal() {
        return adrCodePostal;
    }

    public void setAdrCodePostal(String codePostal) {
//        if (adrCodePostal == null || !adrCodePostal.matches("\\d{5}")) {
//            throw new IllegalArgumentException("Le code postal doit contenir exactement 5 chiffres.");
//        }
        this.adrCodePostal = adrCodePostal;// adresse peut être null
    }

    public String getAdrVille() {
        return adrVille;
    }

    public void setAdrVille(String ville) {
//        if (adrVille == null || adrVille.isBlank()) {
//            throw new IllegalArgumentException("La ville ne peut pas être vide.");
//        }
        this.adrVille = adrVille;// adresse peut être null
    }

//    @Override
//    public String toString() {
//        return "Adresse{" +
//                "adrId=" + adrId +
//                ", rue='" + adrRue + '\'' +
//                ", codePostal='" + adrCodePostal + '\'' +
//                ", ville='" + adrVille + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return  adrRue + " "+ adrCodePostal + " "+ adrVille ;
    }
}
