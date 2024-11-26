package fr.afpa.pompey.cda22045.models;

import java.time.LocalDate;

public class Client extends Utilisateur {
    private Integer cliId;
    private String numeroSecuriteSocial;
    private LocalDate dateNaissance;
    private Mutuelle mutuelle;
    private Medecin medecinTraitant;

    // Constructeur complet
    public Client(Integer cliId, String nom, String prenom, Adresse adresse, String telephone, String email,
                  String numeroSecuriteSocial, LocalDate dateNaissance, Mutuelle mutuelle, Medecin medecinTraitant) {
        super(cliId, nom, prenom, adresse, telephone, email);
        setCliId(cliId);
        setNumeroSecuriteSocial(numeroSecuriteSocial);
        setDateNaissance(dateNaissance);
        setMutuelle(mutuelle);
        setMedecinTraitant(medecinTraitant);
    }

    // Getters et Setters
    public Integer getCliId() {
        return cliId;
    }

    public void setCliId(Integer cliId) {
        if (cliId != null && cliId < 0) {
            throw new IllegalArgumentException("L'id client ne peut pas être négatif.");
        }
        this.cliId = cliId;
    }

    public String getNumeroSecuriteSocial() {
        return numeroSecuriteSocial;
    }

    public void setNumeroSecuriteSocial(String numeroSecuriteSocial) {
        if (numeroSecuriteSocial == null || !numeroSecuriteSocial.matches("\\d{15}")) {
            throw new IllegalArgumentException("Le numéro de sécurité sociale doit contenir 15 chiffres.");
        }
        this.numeroSecuriteSocial = numeroSecuriteSocial;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        if (dateNaissance != null && dateNaissance.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de naissance ne peut pas être dans le futur.");
        }
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
}
