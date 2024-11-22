package fr.afpa.pompey.cda22045.models;

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

    public int getTmTypeId() {
        return tmTypeId;
    }

    public void setTmTypeId(int tmTypeId) {
        this.tmTypeId = tmTypeId;
    }

    public String getTmTypeNom() {
        return tmTypeNom;
    }

    public void setTmTypeNom(String tmTypeNom) {
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
