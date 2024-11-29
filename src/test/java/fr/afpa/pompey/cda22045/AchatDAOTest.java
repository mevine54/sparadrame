package fr.afpa.pompey.cda22045;

import fr.afpa.pompey.cda22045.dao.ClientDAO;
import fr.afpa.pompey.cda22045.models.Achat;
import fr.afpa.pompey.cda22045.dao.AchatDAO;
import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.time.LocalDate;

public class AchatDAOTest {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(DatabaseConnection::closeInstanceDB));

        AchatDAO achatDAO = new AchatDAO();
        ClientDAO clientDAO = new ClientDAO();

        // Vérifier que le client existe
        Client client = clientDAO.getById(1);
        if (client == null) {
            System.out.println("Le client spécifié n'existe pas. Impossible de poursuivre les tests.");
            return;
        }
        System.out.println("Client récupéré : " + client);

        try {
            // Test 1 : Créer un nouvel achat
            Achat newAchat = new Achat(0, "Ordonnance", LocalDate.now(), client); // Valeur corrigée
            Achat createdAchat = achatDAO.create(newAchat);

            if (createdAchat != null) {
                System.out.println("Created Achat: " + createdAchat);

                // Test 2 : Récupérer un achat par ID
                Achat fetchedAchat = achatDAO.getById(createdAchat.getAchId());
                System.out.println("Fetched Achat: " + fetchedAchat);

                // Test 3 : Mettre à jour un achat
                createdAchat.setType("Ordonnance"); // Valeur compatible
                boolean isUpdated = achatDAO.update(createdAchat);
                System.out.println("Achat updated: " + isUpdated);

                // Test 4 : Récupérer tous les achats
                System.out.println("All Achats: " + achatDAO.getAll());

                // Test 5 : Supprimer un achat
                boolean isDeleted = achatDAO.delete(createdAchat.getAchId());
                System.out.println("Achat deleted: " + isDeleted);
            } else {
                System.out.println("Failed to create Achat");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}