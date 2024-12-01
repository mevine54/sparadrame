package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.dao.ClientDAO;
import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.models.Mutuelle;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ClientPanel extends JPanel {
    private JComboBox<String> clientComboBox;
    private JTextArea detailsArea;
    private ClientDAO clientDAO;
    private List<Client> clients;

    public ClientPanel() {
        clientDAO = new ClientDAO();
        setLayout(new GridBagLayout());
        setBackground(new Color(144, 238, 144)); // Couleur verte similaire au design initial
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelSelection = new JLabel("Gestion des clients :");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelSelection, gbc);

        clientComboBox = new JComboBox<>();
        updateClientComboBox();
        gbc.gridx = 1;
        add(clientComboBox, gbc);

        detailsArea = new JTextArea(5, 20);
        detailsArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(detailsScrollPane, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        JButton btnCreer = new JButton("Créer");
        add(btnCreer, gbc);
        gbc.gridx = 1;
        JButton btnModifier = new JButton("Modifier");
        add(btnModifier, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton btnSupprimer = new JButton("Supprimer");
        add(btnSupprimer, gbc);
        gbc.gridx = 1;
        JButton btnRechercher = new JButton("Rechercher");
        add(btnRechercher, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton btnRetour = new JButton("Retour");
        add(btnRetour, gbc);

        clientComboBox.addActionListener(e -> afficherDetailsClient());
        btnCreer.addActionListener(e -> creerClient());
        btnModifier.addActionListener(e -> modifierClient());
        btnSupprimer.addActionListener(e -> supprimerClient());
        btnRechercher.addActionListener(e -> rechercherClient());

        btnRetour.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());
    }

    private void updateClientComboBox() {
        clients = clientDAO.getAll();
        clientComboBox.removeAllItems();
        for (Client client : clients) {
            clientComboBox.addItem(client.getUtiNom() + " " + client.getUtiPrenom());
        }
    }

    private void afficherDetailsClient() {
        int selectedIndex = clientComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            Client client = clients.get(selectedIndex);
            detailsArea.setText("Nom : " + client.getUtiNom() + "."
                    + "Prénom : " + client.getUtiPrenom() + "."
                    + "Adresse : " + (client.getAdresse() != null ? client.getAdresse().getAdrRue() : "N/A") + "."
                    + "Code Postal : " + (client.getAdresse() != null ? client.getAdresse().getAdrCodePostal() : "N/A") + "."
                    + "Ville : " + (client.getAdresse() != null ? client.getAdresse().getAdrVille() : "N/A") + "."
                    + "Téléphone : " + client.getUtiTel() + "."
                    + "Email : " + client.getUtiEmail());
        }
    }

    private void creerClient() {
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField adresseField = new JTextField();
        JTextField codePostalField = new JTextField();
        JTextField villeField = new JTextField();
        JTextField telephoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField dateNaissanceField = new JTextField();
        JTextField numeroSecuField = new JTextField();
        JTextField mutuelleField = new JTextField();
        JTextField medecinField = new JTextField();

        Object[] fields = {
                "Nom :", nomField,
                "Prénom :", prenomField,
                "Adresse :", adresseField,
                "Code Postal :", codePostalField,
                "Ville :", villeField,
                "Téléphone :", telephoneField,
                "Email :", emailField,
                "Date de Naissance (yyyy-MM-dd) :", dateNaissanceField,
                "Numéro de Sécurité Sociale :", numeroSecuField,
                "Mutuelle :", mutuelleField,
                "Médecin :", medecinField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Créer un Client", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();

            // Vérification de l'email
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "L'adresse email est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = new Client(
                    null, // L'ID sera généré automatiquement
                    nomField.getText(),
                    prenomField.getText(),
                    new Adresse(null, adresseField.getText(), codePostalField.getText(), villeField.getText()),
                    telephoneField.getText(),
                    email,
                    numeroSecuField.getText(),
                    LocalDate.parse(dateNaissanceField.getText()),
                    new Mutuelle(null, mutuelleField.getText(), null, "", "", "", 0.0), // Créer une instance vide de Mutuelle si nécessaire
                    new Medecin(null, "", "", null, "", "", "") // Créer une instance vide de Medecin si nécessaire
            );

            boolean success = clientDAO.create(client) != null;
            if (success) {
                updateClientComboBox();
                JOptionPane.showMessageDialog(this, "Client ajouté avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du client.");
            }
        }
    }

    private void modifierClient() {
        int selectedIndex = clientComboBox.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à modifier.");
            return;
        }

        Client client = clients.get(selectedIndex);

        JTextField nomField = new JTextField(client.getUtiNom());
        JTextField prenomField = new JTextField(client.getUtiPrenom());
        JTextField adresseField = new JTextField(client.getAdresse() != null ? client.getAdresse().getAdrRue() : "");
        JTextField codePostalField = new JTextField(client.getAdresse() != null ? client.getAdresse().getAdrCodePostal() : "");
        JTextField villeField = new JTextField(client.getAdresse() != null ? client.getAdresse().getAdrVille() : "");
        JTextField telephoneField = new JTextField(client.getUtiTel());
        JTextField emailField = new JTextField(client.getUtiEmail());
        JTextField dateNaissanceField = new JTextField(client.getDateNaissance().toString());
        JTextField numeroSecuField = new JTextField(client.getNumeroSecuriteSocial());
        JTextField mutuelleField = new JTextField(client.getMutuelle().getMutNom());
        JTextField medecinField = new JTextField(client.getMedecinTraitant() != null ? String.valueOf(client.getMedecinTraitant().getUtiId()) : "");

        Object[] fields = {
                "Nom :", nomField,
                "Prénom :", prenomField,
                "Adresse :", adresseField,
                "Code Postal :", codePostalField,
                "Ville :", villeField,
                "Téléphone :", telephoneField,
                "Email :", emailField,
                "Date de Naissance (yyyy-MM-dd) :", dateNaissanceField,
                "Numéro de Sécurité Sociale :", numeroSecuField,
                "Mutuelle :", mutuelleField,
                "Médecin :", medecinField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Modifier un Client", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();

            // Vérification de l'email
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "L'adresse email est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            client.setUtiNom(nomField.getText());
            client.setUtiPrenom(prenomField.getText());
            client.setAdresse(new Adresse(null, adresseField.getText(), codePostalField.getText(), villeField.getText()));
            client.setUtiTel(telephoneField.getText());
            client.setUtiEmail(email);
            client.setDateNaissance(LocalDate.parse(dateNaissanceField.getText()));
            client.setNumeroSecuriteSocial(numeroSecuField.getText());
            client.setMutuelle(new Mutuelle(client.getMutuelle().getMutId(), mutuelleField.getText(), null, null, null, null, client.getMutuelle().getMutTauxPriseEnCharge()));
            client.setMedecinTraitant(new Medecin(null, "", "", null, "", "", "")); // Remplacer par une instance de médecin appropriée si nécessaire

            boolean success = clientDAO.update(client);
            if (success) {
                updateClientComboBox();
                JOptionPane.showMessageDialog(this, "Client modifié avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification du client.");
            }
        }
    }

    private void supprimerClient() {
        int selectedIndex = clientComboBox.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à supprimer.");
            return;
        }

        Client client = clients.get(selectedIndex);
        int option = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer le client sélectionné ?", "Confirmation de suppression", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            boolean success = clientDAO.delete(client.getUtiId());
            if (success) {
                updateClientComboBox();
                detailsArea.setText("");
                JOptionPane.showMessageDialog(this, "Client supprimé avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du client.");
            }
        }
    }

    private void rechercherClient() {
        String nomRecherche = JOptionPane.showInputDialog(this, "Entrez le nom du client à rechercher :");
        if (nomRecherche != null && !nomRecherche.trim().isEmpty()) {
            for (int i = 0; i < clientComboBox.getItemCount(); i++) {
                if (clientComboBox.getItemAt(i).toLowerCase().contains(nomRecherche.toLowerCase())) {
                    clientComboBox.setSelectedIndex(i);
                    afficherDetailsClient();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Client non trouvé.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
}
