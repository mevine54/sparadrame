package fr.afpa.pompey.cda22045.models;

public class Medecin extends Utilisateur {
    private String numeroAgrement;

    public Medecin() {}

    public Medecin(Integer userId, String nom, String prenom, Adresse adresse, String telephone, String email, String numeroAgrement) {
        super(userId, nom, prenom, adresse, telephone, email);
        setNumeroAgrement(numeroAgrement);
    }

    public String getNumeroAgrement() {
        return numeroAgrement;
    }

    public void setNumeroAgrement(String numeroAgrement) {
        if (numeroAgrement == null || numeroAgrement.isBlank()) {
            throw new IllegalArgumentException("Le numéro d'agrément ne peut pas être vide.");
        }
        this.numeroAgrement = numeroAgrement;
    }

    @Override
    public String toString() {
        return "Medecin{" +
                super.toString() +
                ", numeroAgrement='" + numeroAgrement + '\'' +
                '}';
    }
}
