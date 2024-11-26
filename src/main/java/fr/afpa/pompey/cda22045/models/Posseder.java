package fr.afpa.pompey.cda22045.models;

public class Posseder {
    private Integer possId;
    private Integer utilisateurId;
    private Integer adresseId;
    private String typePossession;

    // Constructeur par défaut
    public Posseder() {
    }

    // Constructeur avec paramètres
    public Posseder(Integer possId, Integer utilisateurId, Integer adresseId, String typePossession) {
        setPossId(possId);
        setUtilisateurId(utilisateurId);
        setAdresseId(adresseId);
        setTypePossession(typePossession);
    }

    // Getters et setters
    public Integer getPossId() {
        return possId;
    }

    public void setPossId(Integer possId) {
        this.possId = possId;
    }

    public Integer getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Integer utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public Integer getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(Integer adresseId) {
        this.adresseId = adresseId;
    }

    public String getTypePossession() {
        return typePossession;
    }

    public void setTypePossession(String typePossession) {
        this.typePossession = typePossession;
    }

    @Override
    public String toString() {
        return "Posseder{" +
                "possId=" + possId +
                ", utilisateurId=" + utilisateurId +
                ", adresseId=" + adresseId +
                ", typePossession='" + typePossession + '\'' +
                '}';
    }
}
