package fr.afpa.pompey.cda22045.models;

import fr.afpa.pompey.cda22045.enums.TypeMedicamentEnum;

import java.time.LocalDate;

public class Medicament {
    private Integer mediId;
    private String mediNom;
    private TypeMedicamentEnum typeMedicament;
    private double mediPrix;
    private LocalDate mediDateMiseEnService;
    private int mediQuantite;

    // Constructeur
    public Medicament(Integer mediId, String mediNom, TypeMedicamentEnum typeMedicament, double mediPrix, LocalDate mediDateMiseEnService, int mediQuantite) {
        setMediId(mediId);
        setMediNom(mediNom);
        setTypeMedicament(typeMedicament);
        setMediPrix(mediPrix);
        setMediDateMiseEnService(mediDateMiseEnService);
        setMediQuantite(mediQuantite);
    }

    // Getters et setters
    public Integer getMediId() {
        return mediId;
    }

    public void setMediId(Integer id) {
        this.mediId = mediId;
    }

    public String getMediNom() {
        return mediNom;
    }

    public void setMediNom(String mediNom) {
        this.mediNom = mediNom;
    }

    public TypeMedicamentEnum getTypeMedicament() {
        return typeMedicament;
    }

    public void setTypeMedicament(TypeMedicamentEnum typeMedicament) {
        this.typeMedicament = typeMedicament;
    }

    public double getMediPrix() {
        return mediPrix;
    }

    public void setMediPrix(double mediPrix) {
        this.mediPrix = mediPrix;
    }

    public LocalDate getMediDateMiseEnService() {
        return mediDateMiseEnService;
    }

    public void setMediDateMiseEnService(LocalDate mediDateMiseEnService) {
        this.mediDateMiseEnService = mediDateMiseEnService;
    }

    public int getMediQuantite() {
        return mediQuantite;
    }

    public void setMediQuantite(int mediQuantite) {
        this.mediQuantite = mediQuantite;
    }

    @Override
    public String toString() {
        return mediNom + " (" + typeMedicament + "), Prix: " + mediPrix + "€, Quantité: " + mediQuantite;
    }
}
