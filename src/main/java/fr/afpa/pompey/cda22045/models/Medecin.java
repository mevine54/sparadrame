package fr.afpa.pompey.cda22045.models;

public class Medecin extends Utilisateur {
    private static final Integer userId = 0;
    private Integer medId;
    private String numeroAgrement;

    // Constructeur
    public Medecin(Integer medId, String nom, String prenom, Adresse adresse, String telephone, String email, String numeroAgrement) {
        super(userId, nom, prenom, adresse, telephone, email);
        setNumeroAgrement(numeroAgrement);
    }

    // Getters et setters

    public Integer getMedId() {
        return medId;
    }

    public void setMedId(Integer medId) {
        this.medId = medId;
    }

    public String getNumeroAgrement() {
        return numeroAgrement;
    }

    public void setNumeroAgrement(String numeroAgrement) {
        this.numeroAgrement = numeroAgrement;
    }

    @Override
    public String toString() {
        return super.toString() + ", Numéro d'agrément: " + numeroAgrement;
    }
}
