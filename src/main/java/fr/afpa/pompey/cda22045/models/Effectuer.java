package fr.afpa.pompey.cda22045.models;

import java.time.LocalDate;

public class Effectuer {
    private Integer id;
    private Integer achatId;
    private Integer utilisateurId;
    private LocalDate dateEffectuee;

    // Constructeur
    public Effectuer(Integer id, Integer achatId, Integer utilisateurId, LocalDate dateEffectuee) {
        setId(id);
        setAchatId(achatId);
        setUtilisateurId(utilisateurId);
        setDateEffectuee(dateEffectuee);
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

    public Integer getAchatId() {
        return achatId;
    }

    public void setAchatId(Integer achatId) {
        if (achatId == null || achatId < 0) {
            throw new IllegalArgumentException("L'ID d'achat doit être un entier positif.");
        }
        this.achatId = achatId;
    }

    public Integer getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Integer utilisateurId) {
        if (utilisateurId == null || utilisateurId < 0) {
            throw new IllegalArgumentException("L'ID utilisateur doit être un entier positif.");
        }
        this.utilisateurId = utilisateurId;
    }

    public LocalDate getDateEffectuee() {
        return dateEffectuee;
    }

    public void setDateEffectuee(LocalDate dateEffectuee) {
        if (dateEffectuee != null && dateEffectuee.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date effectuée ne peut pas être dans le futur.");
        }
        this.dateEffectuee = dateEffectuee;
    }
}
