package fr.afpa.pompey.cda22045.enums;

public enum enumTypeSpecialite {
    CARDIOLOGIE(1, "Cardiologie"),
    DERMATOLOGIE(2, "Dermatologie"),
    PEDIATRIE(3, "Pédiatrie");

    private final int id;
    private final String nom;

    enumTypeSpecialite(int id, String nom) {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID doit être supérieur à 0");
        }
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut pas être nul ou vide");
        }
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public static enumTypeSpecialite fromId(int id) {
        for (enumTypeSpecialite type : enumTypeSpecialite.values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Aucun TypeSpecialite trouvé pour l'ID : " + id);
    }

    public static enumTypeSpecialite fromNom(String nom) {
        for (enumTypeSpecialite type : enumTypeSpecialite.values()) {
            if (type.nom.equalsIgnoreCase(nom.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Aucun TypeSpecialite trouvé pour le nom : " + nom);
    }

    @Override
    public String toString() {
        return nom; // Pour affichage dans un JComboBox
    }
}
