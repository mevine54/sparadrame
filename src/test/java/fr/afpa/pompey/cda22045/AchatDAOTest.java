package fr.afpa.pompey.cda22045;

import fr.afpa.pompey.cda22045.models.Achat;
import fr.afpa.pompey.cda22045.dao.AchatDAO;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.time.LocalDate;

public class AchatDAOTest {

    public static void main(String[] args) {
        // Ajoute un hook pour fermer la connexion après les tests
        Runtime.getRuntime().addShutdownHook(new Thread(DatabaseConnection::closeInstanceDB));

        // Créer une instance de AchatDAO
        AchatDAO achatDAO = new AchatDAO();

        // Test 1 : Créer un nouvel achat
        Achat newAchat = new Achat(0, "Direct", LocalDate.now(), 1); // utilisateur_id = 1
        Achat createdAchat = achatDAO.create(newAchat);
        if (createdAchat != null) {
            System.out.println("Created Achat: " + createdAchat);
        } else {
            System.out.println("Failed to create Achat");
        }

        // Test 2 : Récupérer un achat par ID
        if (createdAchat != null) {
            Achat fetchedAchat = achatDAO.getById(createdAchat.getId());
            System.out.println("Fetched Achat: " + fetchedAchat);
        }

        // Test 3 : Mettre à jour un achat
        if (createdAchat != null) {
            createdAchat.setType("Ordonnance");
            boolean isUpdated = achatDAO.update(createdAchat);
            System.out.println("Achat updated: " + isUpdated);
        }

        // Test 4 : Récupérer tous les achats
        System.out.println("All Achats: " + achatDAO.getAll());

        // Test 5 : Supprimer un achat
        if (createdAchat != null) {
            boolean isDeleted = achatDAO.delete(createdAchat.getId());
            System.out.println("Achat deleted: " + isDeleted);
        }
    }
}
