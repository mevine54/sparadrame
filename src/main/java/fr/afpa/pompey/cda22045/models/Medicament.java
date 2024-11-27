package fr.afpa.pompey.cda22045.models;

import fr.afpa.pompey.cda22045.enums.enumTypeMedicament;

import java.time.LocalDate;

public class Medicament {
    private Integer mediId;
    private String mediNom;
    private enumTypeMedicament typeMedicament;
    private double mediPrix;
    private LocalDate mediDateMiseEnService;
    private int mediQuantite;

    // Constructeur
    public Medicament(Integer mediId, String mediNom, enumTypeMedicament typeMedicament, double mediPrix,
                      LocalDate mediDateMiseEnService, int mediQuantite) {
        setMediId(mediId);
        setMediNom(mediNom);
        setTypeMedicament(typeMedicament);
        setMediPrix(mediPrix);
        setMediDateMiseEnService(mediDateMiseEnService);
        setMediQuantite(mediQuantite);
    }

    // Getters et setters avec validations
    public Integer getMediId() {
        return mediId;
    }

    public void setMediId(Integer mediId) {
        if (mediId != null && mediId < 0) {
            throw new IllegalArgumentException("L'id médicament ne peut pas être négatif.");
        }
        this.mediId = mediId;
    }

    public String getMediNom() {
        return mediNom;
    }

    public void setMediNom(String mediNom) {
        if (mediNom == null || mediNom.isBlank()) {
            throw new IllegalArgumentException("Le nom du médicament ne peut pas être vide.");
        }
        this.mediNom = mediNom;
    }

    public enumTypeMedicament getTypeMedicament() {
        return typeMedicament;
    }

    public void setTypeMedicament(enumTypeMedicament typeMedicament) {
        if (typeMedicament == null) {
            throw new IllegalArgumentException("Le type du médicament ne peut pas être null.");
        }
        this.typeMedicament = typeMedicament;
    }

    public double getMediPrix() {
        return mediPrix;
    }

    public void setMediPrix(double mediPrix) {
        if (mediPrix < 0) {
            throw new IllegalArgumentException("Le prix du médicament doit être positif.");
        }
        this.mediPrix = mediPrix;
    }

    public LocalDate getMediDateMiseEnService() {
        return mediDateMiseEnService;
    }

    public void setMediDateMiseEnService(LocalDate mediDateMiseEnService) {
        if (mediDateMiseEnService != null && mediDateMiseEnService.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de mise en service ne peut pas être dans le futur.");
        }
        this.mediDateMiseEnService = mediDateMiseEnService;
    }

    public int getMediQuantite() {
        return mediQuantite;
    }

    public void setMediQuantite(int mediQuantite) {
        if (mediQuantite < 0) {
            throw new IllegalArgumentException("La quantité doit être positive.");
        }
        this.mediQuantite = mediQuantite;
    }

    @Override
    public String toString() {
        return mediNom + " (" + typeMedicament + "), Prix: " + mediPrix + "€, Quantité: " + mediQuantite;
    }
}
