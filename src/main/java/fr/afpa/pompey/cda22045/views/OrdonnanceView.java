package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.models.Medicament;
import fr.afpa.pompey.cda22045.models.Ordonnance;

import java.util.List;

public class OrdonnanceView {

    // Afficher les détails d'une ordonnance
    public void afficherOrdonnance(Ordonnance ordonnance) {
        System.out.println("Date de l'ordonnance : " + ordonnance.getDate());
        System.out.println("Médecin : " + ordonnance.getMedecin().getUtiNom());
        System.out.println("Patient : " + ordonnance.getPatient().getUtiNom());
        System.out.println("Liste des médicaments : ");
        for (Medicament medicament : ordonnance.getMedicaments()) {
            System.out.println(" - " + medicament.getMediNom() + " (Quantité: " + medicament.getMediQuantite() + ", Catégorie: " + medicament.getTypeMedicament() + ")");
        }
        if (ordonnance.getSpecialiste() != null) {
            System.out.println("Spécialiste : " + ordonnance.getSpecialiste().getUtiNom() + " (" + ordonnance.getSpecialiste().getTypeSpecialite() + ")");
        }
    }

    // Afficher une liste d'ordonnances
    public void afficherListeOrdonnances(List<Ordonnance> ordonnances) {
        for (Ordonnance ordonnance : ordonnances) {
            afficherOrdonnance(ordonnance);
            System.out.println("--------------------");
        }
    }
}
