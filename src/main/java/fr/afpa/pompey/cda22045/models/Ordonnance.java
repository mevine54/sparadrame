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
    public Ordonnance(Integer ordId, LocalDate date, Medecin medecin, Client patient,
                      List<Medicament> medicaments, Specialiste specialiste) {
        setOrdId(ordId);
        setDate(date);
        setMedecin(medecin);
        setPatient(patient);
        setMedicaments(medicaments);
        setSpecialiste(specialiste);
    }

    // Getters et setters avec validations
    public Integer getOrdId() {
        return ordId;
    }

    public void setOrdId(Integer ordId) {
        if (ordId != null && ordId < 0) {
            throw new IllegalArgumentException("L'id de l'ordonnance ne peut pas être négatif.");
        }
        this.ordId = ordId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date != null && date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de l'ordonnance ne peut pas être dans le futur.");
        }
        this.date = date;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        if (medecin == null) {
            throw new IllegalArgumentException("Le médecin ne peut pas être null.");
        }
        this.medecin = medecin;
    }

    public Client getPatient() {
        return patient;
    }

    public void setPatient(Client patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Le patient ne peut pas être null.");
        }
        this.patient = patient;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(List<Medicament> medicaments) {
        if (medicaments == null || medicaments.isEmpty()) {
            throw new IllegalArgumentException("La liste des médicaments ne peut pas être vide.");
        }
        this.medicaments = medicaments;
    }

    public Specialiste getSpecialiste() {
        return specialiste;
    }

    public void setSpecialiste(Specialiste specialiste) {
        if (specialiste == null) {
            throw new IllegalArgumentException("Le spécialiste ne peut pas être null.");
        }
        this.specialiste = specialiste;
    }

    @Override
    public String toString() {
        return "Ordonnance du " + date + " pour le patient: " + patient.getNom() +
                " par le Dr " + medecin.getNom();
    }
}
