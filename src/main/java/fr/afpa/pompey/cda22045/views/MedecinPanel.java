package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.dao.MedecinDAO;
import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Medecin;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MedecinPanel extends JPanel {
    private JComboBox<String> medecinComboBox;
    private JTextArea detailsArea;
    private MedecinDAO medecinDAO;
    private List<Medecin> medecins;

    public MedecinPanel() {
        medecinDAO = new MedecinDAO();
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel labelSelection = new JLabel("Détails des médecins / spécialistes :");
        medecinComboBox = new JComboBox<>();
        updateMedecinComboBox();

        detailsArea = new JTextArea(5, 20);
        detailsArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);

        JButton btnCreer = new JButton("Créer");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnRechercher = new JButton("Rechercher");

        // Ajouter les composants
        add(labelSelection);
        add(medecinComboBox);
        add(new JLabel("Détails du Médecin :"));
        add(detailsScrollPane);
        add(btnCreer);
        add(btnModifier);
        add(btnSupprimer);
        add(btnRechercher);

        // ActionListeners pour les boutons
        medecinComboBox.addActionListener(e -> afficherDetailsMedecin());
        btnCreer.addActionListener(e -> creerMedecin());
        btnModifier.addActionListener(e -> modifierMedecin());
        btnSupprimer.addActionListener(e -> supprimerMedecin());
        btnRechercher.addActionListener(e -> rechercherMedecin());
    }

    private void updateMedecinComboBox() {
        medecins = medecinDAO.getAll();
        medecinComboBox.removeAllItems();
        for (Medecin medecin : medecins) {
            medecinComboBox.addItem(medecin.getUtiNom() + " " + medecin.getUtiPrenom());
        }
    }

    private void afficherDetailsMedecin() {
        int selectedIndex = medecinComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            Medecin medecin = medecins.get(selectedIndex);
            detailsArea.setText("Nom : " + medecin.getUtiNom() + "\n"
                    + "Prénom : " + medecin.getUtiPrenom() + "\n"
                    + "Adresse : " + (medecin.getAdresse() != null ? medecin.getAdresse().getAdrRue() : "N/A") + "\n"
                    + "Code Postal : " + (medecin.getAdresse() != null ? medecin.getAdresse().getAdrCodePostal() : "N/A") + "\n"
                    + "Ville : " + (medecin.getAdresse() != null ? medecin.getAdresse().getAdrVille() : "N/A") + "\n"
                    + "Téléphone : " + medecin.getUtiTel() + "\n"
                    + "Email : " + medecin.getUtiEmail() + "\n"
                    + "Numéro d’Agrément : " + medecin.getMedNumAgreement());
        }
    }

    // Méthode pour créer un médecin (méthode squelettique pour éviter les erreurs)
    private void creerMedecin() {
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField adresseField = new JTextField();
        JTextField codePostalField = new JTextField();
        JTextField villeField = new JTextField();
        JTextField telephoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField numeroAgrementField = new JTextField();

        Object[] fields = {
                "Nom :", nomField,
                "Prénom :", prenomField,
                "Adresse :", adresseField,
                "Code Postal :", codePostalField,
                "Ville :", villeField,
                "Téléphone :", telephoneField,
                "Email :", emailField,
                "Numéro d’Agrément :", numeroAgrementField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Créer un Médecin", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();

            // Vérification de l'email
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "L'adresse email est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return; // Stopper la création si l'email est invalide
            }

            Medecin medecin = new Medecin(
                    null, // L'ID sera généré automatiquement
                    nomField.getText(),
                    prenomField.getText(),
                    new Adresse(null, adresseField.getText(), codePostalField.getText(), villeField.getText()),
                    telephoneField.getText(),
                    email,
                    numeroAgrementField.getText()
            );

            boolean success = medecinDAO.create(medecin) != null;
            if (success) {
                updateMedecinComboBox();
                JOptionPane.showMessageDialog(this, "Médecin ajouté avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du médecin.");
            }
        }
    }

    // Méthode de validation d'email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }

    // Méthode pour modifier un médecin (méthode squelettique pour éviter les erreurs)
    private void modifierMedecin() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité de modification de médecin non implémentée pour le moment.");
    }

    // Méthode pour supprimer un médecin (méthode squelettique pour éviter les erreurs)
    private void supprimerMedecin() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité de suppression de médecin non implémentée pour le moment.");
    }

    // Méthode pour rechercher un médecin (méthode squelettique pour éviter les erreurs)
    private void rechercherMedecin() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité de recherche de médecin non implémentée pour le moment.");
    }
}
