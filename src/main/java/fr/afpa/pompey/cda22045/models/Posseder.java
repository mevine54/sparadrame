package fr.afpa.pompey.cda22045.models;

public class Posseder {
    private Integer possId;
    private Integer utilisateurId;
    private Integer adresseId;
    private String typePossession;

    // Constructeur
    public Posseder(Integer possId, Integer utilisateurId, Integer adresseId, String typePossession) {
        setPossId(possId);
        setUtilisateurId(utilisateurId);
        setAdresseId(adresseId);
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

    public Integer getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Integer utilisateurId) {
        if (utilisateurId == null || utilisateurId < 0) {
            throw new IllegalArgumentException("L'id utilisateur ne peut pas être négatif.");
        }
        this.utilisateurId = utilisateurId;
    }

    public Integer getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(Integer adresseId) {
        if (adresseId == null || adresseId < 0) {
            throw new IllegalArgumentException("L'id adresse ne peut pas être négatif.");
        }
        this.adresseId = adresseId;
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
                ", utilisateurId=" + utilisateurId +
                ", adresseId=" + adresseId +
                ", typePossession='" + typePossession + '\'' +
                '}';
    }
}
