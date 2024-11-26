package fr.afpa.pompey.cda22045.models;

import fr.afpa.pompey.cda22045.enums.Specialite;

public class Specialiste extends Medecin {
    private TypeSpecialiste typeSpecialiste;

    // Constructeur
    public Specialiste(Integer userId, String nom, String prenom, Adresse adresse, String telephone, String email,
                       String numeroAgrement, TypeSpecialiste typeSpecialiste) {
        super(userId, nom, prenom, adresse, telephone, email, numeroAgrement);
        setTypeSpecialiste(typeSpecialiste);
    }

    public TypeSpecialiste getTypeSpecialiste() {
        return typeSpecialiste;
    }

    public void setTypeSpecialiste(TypeSpecialiste typeSpecialiste) {
        if (typeSpecialiste == null) {
            throw new IllegalArgumentException("Le type de spécialiste ne peut pas être null.");
        }
        this.typeSpecialiste = typeSpecialiste;
    }

    @Override
    public String toString() {
        return super.toString() + ", Type de Spécialiste: " +
                (typeSpecialiste != null ? typeSpecialiste.getTypeNom() : "Aucun");
    }
}
