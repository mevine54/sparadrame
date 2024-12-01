package fr.afpa.pompey.cda22045.models;

public class Utilisateur {
    private Integer utiId;
    private String utiNom;
    private String utiPrenom;
    private Adresse adresse;
    private String utiTel;
    private String utiEmail;

    // Constructeur par défaut
    public Utilisateur() {}

    // Constructeur avec paramètres
    public Utilisateur(Integer utiId, String utiNom, String utiPrenom, Adresse adresse, String utiTel, String utiEmail) {
        setUtiId(utiId);
        setUtiNom(utiNom);
        setUtiPrenom(utiPrenom);
        setAdresse(adresse);
        setUtiTel(utiTel);
        setUtiEmail(utiEmail);
    }

    // Getters et setters
    public Integer getUtiId() {
        return utiId;
    }

    public void setUtiId(Integer utiId) {
        if (utiId != null && utiId < 0) {
            throw new IllegalArgumentException("L'id utilisateur ne peut pas être négatif.");
        }
        this.utiId = utiId;
    }

    public String getUtiNom() {
        return utiNom;
    }

    public void setUtiNom(String utiNom) {
        if (utiNom == null || utiNom.isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        this.utiNom = utiNom;
    }

    public String getUtiPrenom() {
        return utiPrenom;
    }

    public void setUtiPrenom(String utiPrenom) {
        if (utiPrenom == null || utiPrenom.isBlank()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        this.utiPrenom = utiPrenom;
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

    public String getUtiTel() {
        return utiTel;
    }

    public void setUtiTel(String utiTel) {
        if (utiTel == null || utiTel.isBlank()) {
            throw new IllegalArgumentException("Le téléphone ne peut pas être vide.");
        }
        this.utiTel = utiTel;
    }

    public String getUtiEmail() {
        return utiEmail;
    }

    public void setUtiEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("L'adresse email est invalide.");
        }
        this.utiEmail = email;
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    @Override
    public String toString() {
        return utiNom + " " + utiPrenom + ", " + adresse.toString() + ", Tel: " + utiTel + ", Email: " + utiEmail;
    }
}
