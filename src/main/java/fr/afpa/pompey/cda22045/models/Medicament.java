package fr.afpa.pompey.cda22045.models;

import fr.afpa.pompey.cda22045.enums.CategorieMedicament;

public class Medicament {
    private int id;
    private String nom;
    private CategorieMedicament categorie;
    private double prix;
    private String dateMiseEnService;
    private int quantite;

    // Constructeur
    public Medicament(int id, String nom, CategorieMedicament categorie, double prix, String dateMiseEnService, int quantite) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.prix = prix;
        this.dateMiseEnService = dateMiseEnService;
        this.quantite = quantite;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public CategorieMedicament getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieMedicament categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDateMiseEnService() {
        return dateMiseEnService;
    }

    public void setDateMiseEnService(String dateMiseEnService) {
        this.dateMiseEnService = dateMiseEnService;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return nom + " (" + categorie + "), Prix: " + prix + "€, Quantité: " + quantite;
    }
}
