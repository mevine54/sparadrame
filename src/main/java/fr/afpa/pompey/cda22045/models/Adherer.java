package fr.afpa.pompey.cda22045.models;

import java.time.LocalDate;

public class Adherer {
    private Integer adhId;
    private Integer clientId;
    private Integer mutuelleId;
    private LocalDate dateAdhesion;
    private String niveauCouverture;

    public Adherer() {}

    public Adherer(Integer adhId, Integer clientId, Integer mutuelleId, LocalDate dateAdhesion, String niveauCouverture) {
        setAdhId(adhId);
        setClientId(clientId);
        setMutuelleId(mutuelleId);
        setDateAdhesion(dateAdhesion);
        setNiveauCouverture(niveauCouverture);
    }

    public Integer getAdhId() {
        return adhId;
    }

    public void setAdhId(Integer adhId) {
        if (adhId != null && adhId < 0) {
            throw new IllegalArgumentException("L'id ne peut pas être négatif.");
        }
        this.adhId = adhId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        if (clientId == null || clientId < 0) {
            throw new IllegalArgumentException("L'id client ne peut pas être nul ou négatif.");
        }
        this.clientId = clientId;
    }

    public Integer getMutuelleId() {
        return mutuelleId;
    }

    public void setMutuelleId(Integer mutuelleId) {
        if (mutuelleId == null || mutuelleId < 0) {
            throw new IllegalArgumentException("L'id mutuelle ne peut pas être nul ou négatif.");
        }
        this.mutuelleId = mutuelleId;
    }

    public LocalDate getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(LocalDate dateAdhesion) {
        if (dateAdhesion != null && dateAdhesion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date d'adhésion ne peut pas être dans le futur.");
        }
        this.dateAdhesion = dateAdhesion;
    }

    public String getNiveauCouverture() {
        return niveauCouverture;
    }

    public void setNiveauCouverture(String niveauCouverture) {
        if (niveauCouverture == null || niveauCouverture.isBlank()) {
            throw new IllegalArgumentException("Le niveau de couverture ne peut pas être vide.");
        }
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



