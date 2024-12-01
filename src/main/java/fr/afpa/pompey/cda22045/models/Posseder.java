package fr.afpa.pompey.cda22045.models;

public class Posseder {
    private Integer possId;
    private Integer utiId;
    private Integer adrId;
    private String typePossession;

    // Constructeur
    public Posseder(Integer possId, Integer utiId, Integer adrId, String typePossession) {
        setPossId(possId);
        setUtiId(utiId);
        setAdrId(adrId);
        setTypePossession(typePossession);
    }

    // Getters et setters avec validations
    public Integer getPossId() {
        return possId;
    }

    public void setPossId(Integer possId) {
        if (possId != null && possId < 0) {
            throw new IllegalArgumentException("L'id de possession ne peut pas être négatif.");
        }
        this.possId = possId;
    }

    public Integer getUtiId() {
        return utiId;
    }

    public void setUtiId(Integer utilisateurId) {
        if (utiId == null || utiId < 0) {
            throw new IllegalArgumentException("L'id utilisateur ne peut pas être négatif.");
        }
        this.utiId = utiId;
    }

    public Integer getAdrId() {
        return adrId;
    }

    public void setAdrId(Integer adrId) {
        if (adrId == null || adrId < 0) {
            throw new IllegalArgumentException("L'id adresse ne peut pas être négatif.");
        }
        this.adrId = adrId;
    }

    public String getTypePossession() {
        return typePossession;
    }

    public void setTypePossession(String typePossession) {
        if (typePossession == null ||
                (!typePossession.equals("Résidentiel") &&
                        !typePossession.equals("Professionnel") &&
                        !typePossession.equals("Autre"))) {
            throw new IllegalArgumentException("Le type de possession est invalide.");
        }
        this.typePossession = typePossession;
    }

    @Override
    public String toString() {
        return "Posseder{" +
                "possId=" + possId +
                ", utilisateurId=" + utiId +
                ", adresseId=" + adrId +
                ", typePossession='" + typePossession + '\'' +
                '}';
    }
}
