package fr.afpa.pompey.cda22045.enums;

public enum enumTypeMedicament {
    PARACETAMOL(1, "Paracétamol"),
    IBUPROFENE(2, "Ibuprofène"),
    ASPIRINE(3, "Aspirine");

    private final int id;
    private final String nom;

    enumTypeMedicament(int id, String nom) {
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

    public static enumTypeMedicament fromId(int id) {
        for (enumTypeMedicament type : enumTypeMedicament.values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Aucun TypeMedicament trouvé pour l'ID : " + id);
    }

    public static enumTypeMedicament fromNom(String nom) {
        for (enumTypeMedicament type : enumTypeMedicament.values()) {
            if (type.nom.equalsIgnoreCase(nom.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Aucun TypeMedicament trouvé pour le nom : " + nom);
    }

    @Override
    public String toString() {
        return nom; // Pour affichage dans un JComboBox
    }
}
