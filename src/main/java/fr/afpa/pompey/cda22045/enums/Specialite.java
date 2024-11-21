package fr.afpa.pompey.cda22045.enums;

// Enum pour la spécialité des médecins
public enum Specialite {
    CARDIOLOGUE,
    DERMATOLOGUE,
    OPHTALMOLOGUE,
    GENERALISTE,
    ORL;

    public static boolean isValid(String value) {
        for (Specialite specialite : Specialite.values()) {
            if (specialite.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
