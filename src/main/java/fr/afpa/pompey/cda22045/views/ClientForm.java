package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.dao.ClientDAO;
import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.models.Mutuelle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ClientForm extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField telephoneField;
    private JTextField emailField;
    private JTextField numeroSecuriteSocialField;
    private JTextField dateNaissanceField;
    private JTextField adresseField;
    private JTextField mutuelleField;
    private JTextField medecinTraitantField;
    private JButton saveButton;

    private ClientDAO clientDAO = new ClientDAO();

    public ClientForm() {
        setTitle("Enregistrer un Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        telephoneField = new JTextField(20);
        emailField = new JTextField(20);
        numeroSecuriteSocialField = new JTextField(20);
        dateNaissanceField = new JTextField(20);
        adresseField = new JTextField(20);
        mutuelleField = new JTextField(20);
        medecinTraitantField = new JTextField(20);
        saveButton = new JButton("Enregistrer");

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
        panel.add(new JLabel("Adresse:"));
        panel.add(adresseField);
        panel.add(new JLabel("Mutuelle:"));
        panel.add(mutuelleField);
        panel.add(new JLabel("Médecin Traitant:"));
        panel.add(medecinTraitantField);
        panel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveClient();
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
        Adresse adresse = new Adresse(Integer.parseInt(adresseField.getText()));
        Mutuelle mutuelle = new Mutuelle(Integer.parseInt(mutuelleField.getText()));
        Medecin medecinTraitant = new Medecin(Integer.parseInt(medecinTraitantField.getText()));

        Client client = new Client(null, nom, prenom, adresse, telephone, email, numeroSecuriteSocial, dateNaissance, mutuelle, medecinTraitant);
        clientDAO.create(client);

        JOptionPane.showMessageDialog(this, "Client enregistré avec succès!");
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

