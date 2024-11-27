package fr.afpa.pompey.cda22045.models;

import fr.afpa.pompey.cda22045.enums.enumTypeSpecialite;

public class Specialiste extends Medecin {
    private enumTypeSpecialite typeSpecialite;

    // Constructeur
    public Specialiste(Integer userId, String nom, String prenom, Adresse adresse, String telephone, String email,
                       String numeroAgrement, enumTypeSpecialite typeSpecialite) {
        super(userId, nom, prenom, adresse, telephone, email, numeroAgrement);
        setTypeSpecialite(typeSpecialite);
    }

    public enumTypeSpecialite getTypeSpecialite() {
        return typeSpecialite;
    }

    public void setTypeSpecialite(enumTypeSpecialite typeSpecialite) {
        if (typeSpecialite == null) {
            throw new IllegalArgumentException("Le type de spécialité ne peut pas être null.");
        }
        this.typeSpecialite = typeSpecialite;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type de Spécialiste: " +
                (typeSpecialite != null ? typeSpecialite.getNom() : "Aucun");
    }
}
