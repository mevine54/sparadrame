package fr.afpa.pompey.cda22045.enums;

// Enum pour la catégorie de médicaments
public enum TypeMedicament {
    DOULEUR,
    ANTIINFLAMMATOIRE,
    ANTIBIOTIQUE,
    VITAMINE,
    CARDIOLOGIE;


    public static boolean isValid(String value) {
        for (TypeMedicament typeMedicament : TypeMedicament.values()) {
            if (typeMedicament.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
