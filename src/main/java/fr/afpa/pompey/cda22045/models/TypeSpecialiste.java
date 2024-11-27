package fr.afpa.pompey.cda22045.models;

public class TypeSpecialiste {
    private Integer tsTypeId;
    private String tsTypeNom;

    // Constructeur vide (par défaut)
    public TypeSpecialiste() {}

    // Constructeur avec tous les champs
    public TypeSpecialiste(Integer tsTypeId, String tsTypeNom) {
        this.tsTypeId = tsTypeId;
        this.tsTypeNom = tsTypeNom;
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

    // Méthode toString() pour affichage
    @Override
    public String toString() {
        return "TypeSpecialiste{" +
                "typeId=" + tsTypeId +
                ", typeNom='" + tsTypeNom + '\'' +
                '}';
    }
}
