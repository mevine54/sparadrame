package fr.afpa.pompey.cda22045.models;

import java.time.LocalDate;

public class Adherer {
    private Integer adhId;
    private Integer clientId;
    private Integer mutuelleId;
    private LocalDate dateAdhesion;
    private String niveauCouverture;

    // Constructeur par défaut
    public Adherer() {
    }

    // Constructeur avec paramètres
    public Adherer(Integer adhId, Integer clientId, Integer mutuelleId, LocalDate dateAdhesion, String niveauCouverture) {
        setAdhId(adhId);
        setClientId(clientId);
        setMutuelleId(mutuelleId);
        setDateAdhesion(dateAdhesion);
        setNiveauCouverture(niveauCouverture);
    }

    // Getters et setters
    public Integer getAdhId() {
        return adhId;
    }

    public void setAdhId(Integer adhId) {
        this.adhId = adhId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getMutuelleId() {
        return mutuelleId;
    }

    public void setMutuelleId(Integer mutuelleId) {
        this.mutuelleId = mutuelleId;
    }

    public LocalDate getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(LocalDate dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public String getNiveauCouverture() {
        return niveauCouverture;
    }

    public void setNiveauCouverture(String niveauCouverture) {
        this.niveauCouverture = niveauCouverture;
    }

    @Override
    public String toString() {
        return "Adherer{" +
                "adhId=" + adhId +
                ", clientId=" + clientId +
                ", mutuelleId=" + mutuelleId +
                ", dateAdhesion=" + dateAdhesion +
                ", niveauCouverture='" + niveauCouverture + '\'' +
                '}';
    }
}
