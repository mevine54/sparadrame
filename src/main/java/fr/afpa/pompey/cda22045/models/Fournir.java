package fr.afpa.pompey.cda22045.models;

public class Fournir {
    private Integer id;
    private Integer specialisteId;
    private Integer typeSpecialisteId;

    // Constructeur
    public Fournir(Integer id, Integer specialisteId, Integer typeSpecialisteId) {
        setId(id);
        setSpecialisteId(specialisteId);
        setTypeSpecialisteId(typeSpecialisteId);
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id != null && id < 0) {
            throw new IllegalArgumentException("L'id ne peut pas être négatif.");
        }
        this.id = id;
    }

    public Integer getSpecialisteId() {
        return specialisteId;
    }

    public void setSpecialisteId(Integer specialisteId) {
        if (specialisteId == null || specialisteId < 0) {
            throw new IllegalArgumentException("L'ID spécialiste doit être un entier positif.");
        }
        this.specialisteId = specialisteId;
    }

    public Integer getTypeSpecialisteId() {
        return typeSpecialisteId;
    }

    public void setTypeSpecialisteId(Integer typeSpecialisteId) {
        if (typeSpecialisteId == null || typeSpecialisteId < 0) {
            throw new IllegalArgumentException("L'ID du type spécialiste doit être un entier positif.");
        }
        this.typeSpecialisteId = typeSpecialisteId;
    }
}
