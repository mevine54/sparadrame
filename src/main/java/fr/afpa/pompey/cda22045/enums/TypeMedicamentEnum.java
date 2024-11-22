package fr.afpa.pompey.cda22045.enums;

// Enum pour la catégorie de médicaments
public enum TypeMedicamentEnum {
    DOULEUR,
    ANTIINFLAMMATOIRE,
    ANTIBIOTIQUE,
    VITAMINE,
    CARDIOLOGIE;


    public static boolean isValid(String value) {
        for (TypeMedicamentEnum typeMedicament : TypeMedicamentEnum.values()) {
            if (typeMedicament.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
