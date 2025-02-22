package fr.afpa.pompey.cda22045.models;

public class Medecin extends Utilisateur {
    private Integer medId;
    private String medNumAgreement;

    // Constructeur complet
    public Medecin(Integer medId, String nom, String prenom, Adresse adresse, String telephone, String email, String medNumAgreement) {
        super(medId, nom, prenom, adresse, telephone, email);
        setMedId(medId);
        setMedNumAgreement(medNumAgreement);
    }

    // Constructeur sans adresse
    public Medecin(Integer medId, String nom, String prenom, String telephone, String email, String medNumAgreement) {
        super(medId, nom, prenom, telephone, email);
        setMedId(medId);
        setMedNumAgreement(medNumAgreement);
    }

    // Constructeur par défaut
    public Medecin() {
    }

    // Getters et Setters
    public Integer getMedId() {
        return medId;
    }

    public void setMedId(Integer medId) {
        if (medId != null && medId < 0) {
            throw new IllegalArgumentException("L'id médecin ne peut pas être négatif.");
        }
        this.medId = medId;
    }

    public String getMedNumAgreement() {
        return medNumAgreement;
    }

    public void setMedNumAgreement(String medNumAgreement) {
        if (medNumAgreement == null) {
            throw new IllegalArgumentException("Le numéro d'agrément doit contenir 8 chiffres.");
        }
        this.medNumAgreement = medNumAgreement;
    }

    @Override
    public String toString() {
        return "Dr." + this.getUtiNom() + " " + this.getUtiPrenom();
    }
}
