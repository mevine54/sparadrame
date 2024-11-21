package fr.afpa.pompey.cda22045.models;

public class TypeSpecialiste {
    private int typeId;
    private String typeNom;

    // Constructeur vide (par défaut)
    public TypeSpecialiste() {}

    // Constructeur avec tous les champs
    public TypeSpecialiste(int typeId, String typeNom) {
        this.typeId = typeId;
        this.typeNom = typeNom;
    }

    // Getters et setters
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeNom() {
        return typeNom;
    }

    public void setTypeNom(String typeNom) {
        this.typeNom = typeNom;
    }

    // Méthode toString() pour affichage
    @Override
    public String toString() {
        return "TypeSpecialiste{" +
                "typeId=" + typeId +
                ", typeNom='" + typeNom + '\'' +
                '}';
    }
}
