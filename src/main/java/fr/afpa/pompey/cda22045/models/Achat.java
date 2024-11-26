package fr.afpa.pompey.cda22045.models;

import java.time.LocalDate;

public class Achat {
    private Integer id;
    private String type; // 'Direct' ou 'Ordonnance'
    private LocalDate dateAchat;
    private Integer utilisateurId; // FK vers Utilisateur

    public Achat() {
    }

    public Achat(Integer id, String type, LocalDate dateAchat, Integer utilisateurId) {
        setId(id);
        setType(type);
        setDateAchat(dateAchat);
        setUtilisateurId(utilisateurId);
    }

    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id != null && id < 0) {
            throw new IllegalArgumentException("L'ID de l'achat ne peut pas être négatif.");
        }
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || (!type.equalsIgnoreCase("Direct") && !type.equalsIgnoreCase("Ordonnance"))) {
            throw new IllegalArgumentException("Le type d'achat doit être 'Direct' ou 'Ordonnance'.");
        }
        this.type = type.trim();
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        if (dateAchat == null) {
            throw new IllegalArgumentException("La date de l'achat ne peut pas être null.");
        }
        if (dateAchat.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de l'achat ne peut pas être dans le futur.");
        }
        this.dateAchat = dateAchat;
    }

    public Integer getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Integer utilisateurId) {
        if (utilisateurId != null && utilisateurId <= 0) {
            throw new IllegalArgumentException("L'ID de l'utilisateur doit être positif.");
        }
        this.utilisateurId = utilisateurId;
    }

    @Override
    public String toString() {
        return "Achat{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", dateAchat=" + dateAchat +
                ", utilisateurId=" + utilisateurId +
                '}';
    }
}
