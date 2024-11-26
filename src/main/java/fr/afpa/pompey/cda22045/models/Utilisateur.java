package fr.afpa.pompey.cda22045.models;

public class Utilisateur {
    private Integer userId;
    private String nom;
    private String prenom;
    private Adresse adresse;
    private String telephone;
    private String email;

    // Constructeur par défaut
    public Utilisateur() {}

    // Constructeur avec paramètres
    public Utilisateur(Integer userId, String nom, String prenom, Adresse adresse, String telephone, String email) {
        setUserId(userId);
        setNom(nom);
        setPrenom(prenom);
        setAdresse(adresse);
        setTelephone(telephone);
        setEmail(email);
    }

    // Getters et setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        if (userId != null && userId < 0) {
            throw new IllegalArgumentException("L'id utilisateur ne peut pas être négatif.");
        }
        this.userId = userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        if (prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        this.prenom = prenom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        if (adresse == null) {
            throw new IllegalArgumentException("L'adresse ne peut pas être null.");
        }
        this.adresse = adresse;
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
            throw new IllegalArgumentException("L'adresse email est invalide.");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return nom + " " + prenom + ", " + adresse.toString() + ", Tel: " + telephone + ", Email: " + email;
    }
}
