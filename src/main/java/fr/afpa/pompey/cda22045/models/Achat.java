package fr.afpa.pompey.cda22045.models;

import java.util.Date;

public class Achat {
    private int id;
    private String type; // 'Direct' or 'Ordonnance'
    private Date dateAchat;
    private int utilisateurId; // FK to Utilisateur

    public Achat() {
    }

    public Achat(int id, String type, Date dateAchat, int utilisateurId) {
        this.id = id;
        this.type = type;
        this.dateAchat = dateAchat;
        this.utilisateurId = utilisateurId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
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

