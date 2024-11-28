package fr.afpa.pompey.cda22045.models;

import java.time.LocalDate;

public class Achat {
    private Integer achId;
    private String type; // 'Direct' ou 'Ordonnance'
    private LocalDate dateAchat;
    private Client client; // FK vers Utilisateur

    public Achat() {
    }

    public Achat(Integer achId, String type, LocalDate dateAchat, Client client) {
        setAchId(achId);
        setType(type);
        setDateAchat(dateAchat);
        setClient(client);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // Getters et setters
    public Integer getAchId() {
        return achId;
    }

    public void setAchId(Integer achId) {
        if (achId != null && achId < 0) {
            throw new IllegalArgumentException("L'ID de l'achat ne peut pas être négatif.");
        }
        this.achId = achId;
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



    @Override
    public String toString() {
        return "Achat{" +
                "id=" + achId +
                ", type='" + type + '\'' +
                ", dateAchat=" + dateAchat +
                ", clientId=" + client.getCliId() +
                '}';
    }
}
