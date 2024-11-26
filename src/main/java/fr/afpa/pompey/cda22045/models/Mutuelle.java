package fr.afpa.pompey.cda22045.models;

public class Mutuelle {
    private Integer mutId;
    private String nom;
    private Adresse adresse;
    private String telephone;
    private String email;
    private String departement;
    private double tauxPriseEnCharge;

    // Constructeur
    public Mutuelle(Integer mutId, String nom, Adresse adresse, String telephone, String email, String departement, double tauxPriseEnCharge) {
        setMutId(mutId);
        setNom(nom);
        setAdresse(adresse);
        setTelephone(telephone);
        setEmail(email);
        setDepartement(departement);
        setTauxPriseEnCharge(tauxPriseEnCharge);
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom de la mutuelle ne peut pas être vide.");
        }
        this.nom = nom;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        if (telephone == null || telephone.isBlank()) {
            throw new IllegalArgumentException("Le téléphone ne peut pas être vide.");
        }
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.\\w{2,}$")) {
            throw new IllegalArgumentException("L'email est invalide.");
        }
        this.email = email;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        if (departement == null || departement.isBlank()) {
            throw new IllegalArgumentException("Le département ne peut pas être vide.");
        }
        this.departement = departement;
    }

    public double getTauxPriseEnCharge() {
        return tauxPriseEnCharge;
    }

    public void setTauxPriseEnCharge(double tauxPriseEnCharge) {
        if (tauxPriseEnCharge < 0 || tauxPriseEnCharge > 100) {
            throw new IllegalArgumentException("Le taux de prise en charge doit être compris entre 0 et 100.");
        }
        this.tauxPriseEnCharge = tauxPriseEnCharge;
    }

    @Override
    public String toString() {
        return "Mutuelle{" +
                "mutId=" + mutId +
                ", nom='" + nom + '\'' +
                ", adresse=" + adresse +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", departement='" + departement + '\'' +
                ", tauxPriseEnCharge=" + tauxPriseEnCharge +
                '}';
    }
}
