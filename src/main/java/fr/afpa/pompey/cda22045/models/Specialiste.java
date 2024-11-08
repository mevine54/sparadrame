package fr.afpa.pompey.cda22045.models;

import fr.afpa.pompey.cda22045.enums.Specialite;

public class Specialiste extends Medecin {
    private int id;
    private Specialite specialite;

    // Constructeur
    public Specialiste(int id, String nom, String prenom, Adresse adresse, String telephone, String email,
                       String numeroAgrement, Specialite specialite) {
        super(id, nom, prenom, adresse, telephone, email, numeroAgrement);
        this.specialite = specialite;
    }

    // Getter pour la spécialité
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return super.toString() + ", Spécialité: " + specialite;
    }
}
