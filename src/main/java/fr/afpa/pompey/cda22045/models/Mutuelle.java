package fr.afpa.pompey.cda22045.models;

public class Mutuelle {
    private Integer mutId;
    private String mutNom;
    private Adresse adresse;
    private String mutTel;
    private String mutEmail;
    private String mutDepartement;
    private double mutTauxPriseEnCharge;

    // Constructeur
    public Mutuelle(Integer mutId, String mutNom, Adresse adresse, String mutTel, String mutEmail, String mutDepartement, double mutTauxPriseEnCharge) {
        setMutId(mutId);
        setMutNom(mutNom);
        setAdresse(adresse);
        setMutTel(mutTel);
        setMutEmail(mutEmail);
        setMutDepartement(mutDepartement);
        setMutTauxPriseEnCharge(mutTauxPriseEnCharge);
    }

    // Getters et setters avec validations
    public Integer getMutId() {
        return mutId;
    }

    public void setMutId(Integer mutId) {
        if (mutId != null && mutId < 0) {
            throw new IllegalArgumentException("L'id mutuelle ne peut pas être négatif.");
        }
        this.mutId = mutId;
    }

    public String getMutNom() {
        return mutNom;
    }

    public void setMutNom(String mutNom) {
        if (mutNom == null || mutNom.isBlank()) {
            throw new IllegalArgumentException("Le nom de la mutuelle ne peut pas être vide.");
        }
        this.mutNom = mutNom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        if (adresse == null) {
            System.out.println("Attention : l'adresse de la mutuelle est null.");
        }
        this.adresse = adresse; // Autorise null si nécessaire
    }

    public String getMutTel() {
        return mutTel;
    }

    public void setMutTel(String mutTel) {
        if (mutTel == null || mutTel.isBlank()) {
            throw new IllegalArgumentException("Le téléphone ne peut pas être vide.");
        }
        this.mutTel = mutTel;
    }

    public String getMutEmail() {
        return mutEmail;
    }

    public void setMutEmail(String mutEmail) {
        if (mutEmail == null || !mutEmail.matches("^[\\w.%+-]+@[\\w.-]+\\.\\w{2,}$")) {
            throw new IllegalArgumentException("L'email est invalide.");
        }
        this.mutEmail = mutEmail;
    }

    public String getMutDepartement() {
        return mutDepartement;
    }

    public void setMutDepartement(String mutDepartement) {
        if (mutDepartement == null || mutDepartement.isBlank()) {
            throw new IllegalArgumentException("Le département ne peut pas être vide.");
        }
        this.mutDepartement = mutDepartement;
    }

    public double getMutTauxPriseEnCharge() {
        return mutTauxPriseEnCharge;
    }

    public void setMutTauxPriseEnCharge(double mutTauxPriseEnCharge) {
        if (mutTauxPriseEnCharge < 0 || mutTauxPriseEnCharge > 100) {
            throw new IllegalArgumentException("Le taux de prise en charge doit être compris entre 0 et 100.");
        }
        this.mutTauxPriseEnCharge = mutTauxPriseEnCharge;
    }

    @Override
    public String toString() {
        return "Mutuelle{" +
                "mutId=" + mutId +
                ", nom='" + mutNom + '\'' +
                ", adresse=" + adresse +
                ", telephone='" + mutTel + '\'' +
                ", email='" + mutEmail + '\'' +
                ", departement='" + mutDepartement + '\'' +
                ", tauxPriseEnCharge=" + mutTauxPriseEnCharge +
                '}';
    }
}
