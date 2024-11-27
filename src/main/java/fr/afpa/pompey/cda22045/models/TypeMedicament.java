package fr.afpa.pompey.cda22045.models;

import fr.afpa.pompey.cda22045.enums.enumTypeMedicament;

public class TypeMedicament {
    private Integer tmTypeId;
    private String tmTypeNom;

    // Constructeur vide par défaut
    public TypeMedicament() {}

    // Constructeur avec tous les champs
    public TypeMedicament(Integer tmTypeId, String tmTypeNom) {
        setTmTypeId(tmTypeId);
        setTmTypeNom(tmTypeNom);
    }

    // Convertir un enum en modèle
    public static TypeMedicament fromEnum(enumTypeMedicament enumType) {
        return new TypeMedicament(enumType.getId(), enumType.getNom());
    }

    // Convertir un modèle en enum
    public static enumTypeMedicament toEnum(TypeMedicament model) {
        for (enumTypeMedicament enumType : enumTypeMedicament.values()) {
            if (enumType.getId() == model.getTmTypeId()) {
                return enumType;
            }
        }
        throw new IllegalArgumentException("Aucun enum trouvé pour l'ID : " + model.getTmTypeId());
    }

    public Integer getTmTypeId() {
        return tmTypeId;
    }

    public void setTmTypeId(Integer tmTypeId) {
        if (tmTypeId == null && tmTypeId < 0) {
            throw new IllegalArgumentException("TmTypeId is null or negative");
        }
        this.tmTypeId = tmTypeId;
    }

    public String getTmTypeNom() {
        return tmTypeNom;
    }

    public void setTmTypeNom(String tmTypeNom) {
        if (tmTypeNom == null || tmTypeNom.isBlank()) {
            throw new IllegalArgumentException("TmTypeNom is null or empty");
        }
        this.tmTypeNom = tmTypeNom;
    }

    // Méthode toString() pour affichage
    @Override
    public String toString() {
        return "TypeMedicament{" +
                "typeId=" + tmTypeId +
                ", typeNom='" + tmTypeNom + '\'' +
                '}';
    }
}
