package fr.afpa.pompey.cda22045.models;

import java.time.LocalDate;

public class Client extends Utilisateur {
    private static final Integer userId = 0;
    private Integer cliId;
    private String numeroSecuriteSocial;
    private LocalDate dateNaissance;
    private Mutuelle mutuelle;
    private Medecin medecinTraitant;

    // Constructeur
    public Client(Integer cliId, String nom, String prenom, Adresse adresse, String telephone, String email,
                  String numeroSecuriteSocial, LocalDate dateNaissance, Mutuelle mutuelle, Medecin medecinTraitant) {
        super(userId, nom, prenom, adresse, telephone, email);
        setNumeroSecuriteSocial(numeroSecuriteSocial);
        setDateNaissance(dateNaissance);
        setMutuelle(mutuelle);
        setMedecinTraitant(medecinTraitant);
    }

    // Getters et setters

    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        this.cliId = cliId;
    }

    public String getNumeroSecuriteSocial() {
        return numeroSecuriteSocial;
    }

    public void setNumeroSecuriteSocial(String numeroSecuriteSocial) {
        this.numeroSecuriteSocial = numeroSecuriteSocial;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Mutuelle getMutuelle() {
        return mutuelle;
    }

    public void setMutuelle(Mutuelle mutuelle) {
        this.mutuelle = mutuelle;
    }

    public Medecin getMedecinTraitant() {
        return medecinTraitant;
    }

    public void setMedecinTraitant(Medecin medecinTraitant) {
        this.medecinTraitant = medecinTraitant;
    }

    @Override
    public String toString() {
        return super.toString() + ", Numéro SS: " + numeroSecuriteSocial + ", Date de naissance: " + dateNaissance;
    }

    public String getNom() {
        return super.getNom();
    }

}
