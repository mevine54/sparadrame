package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.dao.AdresseDAO;
import fr.afpa.pompey.cda22045.dao.ClientDAO;
import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.models.Mutuelle;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public class ClientForm extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField telephoneField;
    private JTextField emailField;
    private JTextField numeroSecuriteSocialField;
    private JFormattedTextField dateNaissanceField;
    private JTextField rueField;
    private JTextField codePostalField;
    private JTextField villeField;
    private JTextField mutuelleNomField;
    private JTextField mutuelleTelephoneField;
    private JTextField mutuelleEmailField;
    private JTextField mutuelleDepartementField;
    private JTextField mutuelleTauxField;
    private JTextField medecinNomField;
    private JTextField medecinPrenomField;
    private JTextField medecinTelephoneField;
    private JTextField medecinEmailField;
    private JTextField medecinNumeroAgrementField;
    private JButton saveButton;
    private JButton updateButton;
    private JButton getByIdButton;
    private JButton getAllButton;
    private JButton deleteButton;
    private JTextField clientIdField;

    private ClientDAO clientDAO = new ClientDAO();
    private AdresseDAO adresseDAO = new AdresseDAO();

    public ClientForm() {
        setTitle("Enregistrer un Client");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        telephoneField = new JTextField(20);
        emailField = new JTextField(20);
        numeroSecuriteSocialField = new JTextField(20);
        try {
            MaskFormatter dateMask = new MaskFormatter("####-##-##");
            dateMask.setPlaceholderCharacter('_');
            dateNaissanceField = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rueField = new JTextField(20);
        codePostalField = new JTextField(20);
        villeField = new JTextField(20);
        mutuelleNomField = new JTextField(20);
        mutuelleTelephoneField = new JTextField(20);
        mutuelleEmailField = new JTextField(20);
        mutuelleDepartementField = new JTextField(20);
        mutuelleTauxField = new JTextField(20);
        medecinNomField = new JTextField(20);
        medecinPrenomField = new JTextField(20);
        medecinTelephoneField = new JTextField(20);
        medecinEmailField = new JTextField(20);
        medecinNumeroAgrementField = new JTextField(20);
        saveButton = new JButton("Enregistrer");
        updateButton = new JButton("Mettre à jour");
        getByIdButton = new JButton("Obtenir par ID");
        getAllButton = new JButton("Obtenir tous");
        deleteButton = new JButton("Supprimer");
        clientIdField = new JTextField(20);

        panel.add(new JLabel("ID Client:"));
        panel.add(clientIdField);
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Téléphone:"));
        panel.add(telephoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Numéro de Sécurité Social:"));
        panel.add(numeroSecuriteSocialField);
        panel.add(new JLabel("Date de Naissance (AAAA-MM-JJ):"));
        panel.add(dateNaissanceField);
        panel.add(new JLabel("Rue:"));
        panel.add(rueField);
        panel.add(new JLabel("Code Postal:"));
        panel.add(codePostalField);
        panel.add(new JLabel("Ville:"));
        panel.add(villeField);
        panel.add(new JLabel("Nom de la Mutuelle:"));
        panel.add(mutuelleNomField);
        panel.add(new JLabel("Téléphone de la Mutuelle:"));
        panel.add(mutuelleTelephoneField);
        panel.add(new JLabel("Email de la Mutuelle:"));
        panel.add(mutuelleEmailField);
        panel.add(new JLabel("Département de la Mutuelle:"));
        panel.add(mutuelleDepartementField);
        panel.add(new JLabel("Taux de prise en charge de la Mutuelle:"));
        panel.add(mutuelleTauxField);
        panel.add(new JLabel("Nom du Médecin:"));
        panel.add(medecinNomField);
        panel.add(new JLabel("Prénom du Médecin:"));
        panel.add(medecinPrenomField);
        panel.add(new JLabel("Téléphone du Médecin:"));
        panel.add(medecinTelephoneField);
        panel.add(new JLabel("Email du Médecin:"));
        panel.add(medecinEmailField);
        panel.add(new JLabel("Numéro d'agrément du Médecin:"));
        panel.add(medecinNumeroAgrementField);
        panel.add(saveButton);
        panel.add(updateButton);
        panel.add(getByIdButton);
        panel.add(getAllButton);
        panel.add(deleteButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveClient();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClient();
            }
        });

        getByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getClientById();
            }
        });

        getAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAllClients();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        add(panel);
    }

    private void saveClient() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();
        String numeroSecuriteSocial = numeroSecuriteSocialField.getText();
        LocalDate dateNaissance = LocalDate.parse(dateNaissanceField.getText());
        String rue = rueField.getText();
        String codePostal = codePostalField.getText();
        String ville = villeField.getText();
        String mutuelleNom = mutuelleNomField.getText();
        String mutuelleTelephone = mutuelleTelephoneField.getText();
        String mutuelleEmail = mutuelleEmailField.getText();
        String mutuelleDepartement = mutuelleDepartementField.getText();
        double mutuelleTaux = Double.parseDouble(mutuelleTauxField.getText());
        String medecinNom = medecinNomField.getText();
        String medecinPrenom = medecinPrenomField.getText();
        String medecinTelephone = medecinTelephoneField.getText();
        String medecinEmail = medecinEmailField.getText();
        String medecinNumeroAgrement = medecinNumeroAgrementField.getText();

        // Créez et enregistrez l'adresse
        Adresse adresse = new Adresse(null, rue, codePostal, ville);
        adresse = adresseDAO.create(adresse);

        // Assurez-vous que l'adresse a été correctement enregistrée
        if (adresse.getAdrId() == 0) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de l'adresse.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créez une mutuelle temporaire avec un ID nul
        Mutuelle mutuelle = new Mutuelle(null, mutuelleNom, adresse, mutuelleTelephone, mutuelleEmail, mutuelleDepartement, mutuelleTaux);

        // Créez un médecin temporaire avec un ID nul
        Medecin medecinTraitant = new Medecin(null, medecinNom, medecinPrenom, adresse, medecinTelephone, medecinEmail, medecinNumeroAgrement);

        // Créez un client avec les informations temporaires
        Client client = new Client(null, nom, prenom, adresse, telephone, email, numeroSecuriteSocial, dateNaissance, mutuelle, medecinTraitant);

        // Enregistrez le client dans la base de données
        clientDAO.create(client);

        JOptionPane.showMessageDialog(this, "Client enregistré avec succès!");
    }

    private void updateClient() {
        String clientIdStr = clientIdField.getText();
        if (clientIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer l'ID du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int clientId = Integer.parseInt(clientIdStr);
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();
        String numeroSecuriteSocial = numeroSecuriteSocialField.getText();
        LocalDate dateNaissance = LocalDate.parse(dateNaissanceField.getText());
        String rue = rueField.getText();
        String codePostal = codePostalField.getText();
        String ville = villeField.getText();
        String mutuelleNom = mutuelleNomField.getText();
        String mutuelleTelephone = mutuelleTelephoneField.getText();
        String mutuelleEmail = mutuelleEmailField.getText();
        String mutuelleDepartement = mutuelleDepartementField.getText();
        double mutuelleTaux = Double.parseDouble(mutuelleTauxField.getText());
        String medecinNom = medecinNomField.getText();
        String medecinPrenom = medecinPrenomField.getText();
        String medecinTelephone = medecinTelephoneField.getText();
        String medecinEmail = medecinEmailField.getText();
        String medecinNumeroAgrement = medecinNumeroAgrementField.getText();

        // Créez et enregistrez l'adresse
        Adresse adresse = new Adresse(null, rue, codePostal, ville);
        adresse = adresseDAO.create(adresse);

        // Assurez-vous que l'adresse a été correctement enregistrée
        if (adresse.getAdrId() == 0) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement de l'adresse.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Créez une mutuelle temporaire avec un ID nul
        Mutuelle mutuelle = new Mutuelle(null, mutuelleNom, adresse, mutuelleTelephone, mutuelleEmail, mutuelleDepartement, mutuelleTaux);

        // Créez un médecin temporaire avec un ID nul
        Medecin medecinTraitant = new Medecin(null, medecinNom, medecinPrenom, adresse, medecinTelephone, medecinEmail, medecinNumeroAgrement);

        // Créez un client avec les informations temporaires
        Client client = new Client(clientId, nom, prenom, adresse, telephone, email, numeroSecuriteSocial, dateNaissance, mutuelle, medecinTraitant);

        // Mettez à jour le client dans la base de données
        if (clientDAO.update(client)) {
            JOptionPane.showMessageDialog(this, "Client mis à jour avec succès!");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getClientById() {
        String clientIdStr = clientIdField.getText();
        if (clientIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer l'ID du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int clientId = Integer.parseInt(clientIdStr);
        Client client = clientDAO.getById(clientId);

        if (client != null) {
            nomField.setText(client.getNom());
            prenomField.setText(client.getPrenom());
            telephoneField.setText(client.getTelephone());
            emailField.setText(client.getEmail());
            numeroSecuriteSocialField.setText(client.getNumeroSecuriteSocial());
            dateNaissanceField.setText(client.getDateNaissance().toString());
            rueField.setText(client.getAdresse().getRue());
            codePostalField.setText(client.getAdresse().getCodePostal());
            villeField.setText(client.getAdresse().getVille());
            mutuelleNomField.setText(client.getMutuelle().getNom());
            mutuelleTelephoneField.setText(client.getMutuelle().getTelephone());
            mutuelleEmailField.setText(client.getMutuelle().getEmail());
            mutuelleDepartementField.setText(client.getMutuelle().getDepartement());
            mutuelleTauxField.setText(String.valueOf(client.getMutuelle().getTauxPriseEnCharge()));
            medecinNomField.setText(client.getMedecinTraitant().getNom());
            medecinPrenomField.setText(client.getMedecinTraitant().getPrenom());
            medecinTelephoneField.setText(client.getMedecinTraitant().getTelephone());
            medecinEmailField.setText(client.getMedecinTraitant().getEmail());
            medecinNumeroAgrementField.setText(client.getMedecinTraitant().getNumeroAgrement());
        } else {
            JOptionPane.showMessageDialog(this, "Aucun client trouvé avec cet ID.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getAllClients() {
        List<Client> clients = clientDAO.getAll();
        StringBuilder clientInfo = new StringBuilder();

        for (Client client : clients) {
            clientInfo.append("ID: ").append(client.getUserId()).append("\n");
            clientInfo.append("Nom: ").append(client.getNom()).append("\n");
            clientInfo.append("Prénom: ").append(client.getPrenom()).append("\n");
            clientInfo.append("Téléphone: ").append(client.getTelephone()).append("\n");
            clientInfo.append("Email: ").append(client.getEmail()).append("\n");
            clientInfo.append("Numéro de Sécurité Social: ").append(client.getNumeroSecuriteSocial()).append("\n");
            clientInfo.append("Date de Naissance: ").append(client.getDateNaissance()).append("\n");
            clientInfo.append("Adresse: ").append(client.getAdresse()).append("\n");
            clientInfo.append("Mutuelle: ").append(client.getMutuelle()).append("\n");
            clientInfo.append("Médecin Traitant: ").append(client.getMedecinTraitant()).append("\n\n");
        }

        JOptionPane.showMessageDialog(this, clientInfo.toString(), "Liste des Clients", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteClient() {
        String clientIdStr = clientIdField.getText();
        if (clientIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer l'ID du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int clientId = Integer.parseInt(clientIdStr);
        if (clientDAO.delete(clientId)) {
            JOptionPane.showMessageDialog(this, "Client supprimé avec succès!");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientForm().setVisible(true);
            }
        });
    }
}
