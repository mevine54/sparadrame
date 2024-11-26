package fr.afpa.pompey.cda22045.models;

public class Delivrer {
    private Integer id;
    private Integer ordonnanceId;
    private Integer medicamentId;
    private Integer quantite;

    // Constructeur
    public Delivrer(Integer id, Integer ordonnanceId, Integer medicamentId, Integer quantite) {
        setId(id);
        setOrdonnanceId(ordonnanceId);
        setMedicamentId(medicamentId);
        setQuantite(quantite);
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

    public Integer getOrdonnanceId() {
        return ordonnanceId;
    }

    public void setOrdonnanceId(Integer ordonnanceId) {
        if (ordonnanceId == null || ordonnanceId < 0) {
            throw new IllegalArgumentException("L'ID ordonnance doit être un entier positif.");
        }
        this.ordonnanceId = ordonnanceId;
    }

    public Integer getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(Integer medicamentId) {
        if (medicamentId == null || medicamentId < 0) {
            throw new IllegalArgumentException("L'ID médicament doit être un entier positif.");
        }
        this.medicamentId = medicamentId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être strictement positive.");
        }
        this.quantite = quantite;
    }
}
