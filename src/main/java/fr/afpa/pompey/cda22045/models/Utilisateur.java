package fr.afpa.pompey.cda22045.models;

public class Utilisateur {
    private Integer userId;
    private String nom;
    private String prenom;
    private Adresse adresse;
    private String telephone;
    private String email;

    // Constructeur
    public Utilisateur(Integer userId, String nom, String prenom, Adresse adresse, String telephone, String email) {
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
    }

    // Getters et setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + ", " + adresse.toString() + ", Tel: " + telephone + ", Email: " + email;
    }
}
