package fr.afpa.pompey.cda22045.models;

import fr.afpa.pompey.cda22045.enums.enumTypeSpecialite;

public class TypeSpecialite {
    private Integer tsTypeId;
    private String tsTypeNom;

    // Constructeur vide (par défaut)
    public TypeSpecialite() {}

    // Constructeur avec tous les champs
    public TypeSpecialite(Integer tsTypeId, String tsTypeNom) {
        this.tsTypeId = tsTypeId;
        this.tsTypeNom = tsTypeNom;
    }

    // Convertir un enum en modèle
    public static TypeSpecialite fromEnum(enumTypeSpecialite enumType) {
        return new TypeSpecialite(enumType.getId(), enumType.getNom());
    }

    // Convertir un modèle en enum
    public static enumTypeSpecialite toEnum(TypeSpecialite model) {
        for (enumTypeSpecialite enumType : enumTypeSpecialite.values()) {
            if (enumType.getId() == model.getTsTypeId()) {
                return enumType;
            }
        }
        throw new IllegalArgumentException("Aucun enum trouvé pour l'ID : " + model.getTsTypeId());
    }

    // Getters et setters
    public Integer getTsTypeId() {
        return tsTypeId;
    }

    public void setTsTypeId(Integer tsTypeId) {
        if (tsTypeId < 0) {
            throw new IllegalArgumentException("TsTypeId cannot be less than 0");
        }
        this.tsTypeId = tsTypeId;
    }

    public String getTsTypeNom() {
        return tsTypeNom;
    }

    public void setTsTypeNom(String tsTypeNom) {
        if (tsTypeNom == null || tsTypeNom.isBlank()) {
            throw new IllegalArgumentException("TsTypeNom cannot be empty");
        }
        this.tsTypeNom = tsTypeNom;
    }


    @Override
    public String toString() {
        return "TypeSpecialiste{" +
                "typeId=" + tsTypeId +
                ", typeNom='" + tsTypeNom + '\'' +
                '}';
    }

}
