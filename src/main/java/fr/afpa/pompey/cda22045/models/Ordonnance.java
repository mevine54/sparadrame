package fr.afpa.pompey.cda22045.models;

import java.time.LocalDate;
import java.util.List;

public class Ordonnance {
    private Integer ordId;
    private LocalDate date;
    private Medecin medecin;
    private Client patient;
    private List<Medicament> medicaments;
    private Specialiste specialiste;

    // Constructeur
    public Ordonnance(Integer ordId, LocalDate date, Medecin medecin, Client patient, List<Medicament> medicaments, Specialiste specialiste) {
        setOrdId(ordId);
        setDate(date);
        setMedecin(medecin);
        setPatient(patient);
        setMedicaments(medicaments);
        setSpecialiste(specialiste);
    }

    // Getters et setters
    public Integer getOrdId() {
        return ordId;
    }

    public void setOrdId(Integer ordId) {
        this.ordId = ordId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Client getPatient() {
        return patient;
    }

    public void setPatient(Client patient) {
        this.patient = patient;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(List<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    public Specialiste getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(Specialiste specialiste) {
        this.specialiste = specialiste;
    }

    @Override
    public String toString() {
        return "Ordonnance du " + date + " pour le patient: " + patient.getNom() + " par le Dr " + medecin.getNom();
    }
}
