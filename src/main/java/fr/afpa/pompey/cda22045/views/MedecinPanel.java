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
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelSelection = new JLabel("Gestion des médecins / spécialistes :");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelSelection, gbc);

        medecinComboBox = new JComboBox<>();
        updateMedecinComboBox();
        gbc.gridx = 1;
        add(medecinComboBox, gbc);

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

        medecinComboBox.addActionListener(e -> afficherDetailsMedecin());
        btnCreer.addActionListener(e -> creerMedecin());
        btnModifier.addActionListener(e -> modifierMedecin());
        btnSupprimer.addActionListener(e -> supprimerMedecin());
        btnRechercher.addActionListener(e -> rechercherMedecin());

        btnRetour.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());
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
                return;
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

    private void modifierMedecin() {
        int selectedIndex = medecinComboBox.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médecin à modifier.");
            return;
        }

        Medecin medecin = medecins.get(selectedIndex);

        JTextField nomField = new JTextField(medecin.getUtiNom());
        JTextField prenomField = new JTextField(medecin.getUtiPrenom());
        JTextField adresseField = new JTextField(medecin.getAdresse() != null ? medecin.getAdresse().getAdrRue() : "");
        JTextField codePostalField = new JTextField(medecin.getAdresse() != null ? medecin.getAdresse().getAdrCodePostal() : "");
        JTextField villeField = new JTextField(medecin.getAdresse() != null ? medecin.getAdresse().getAdrVille() : "");
        JTextField telephoneField = new JTextField(medecin.getUtiTel());
        JTextField emailField = new JTextField(medecin.getUtiEmail());
        JTextField numeroAgrementField = new JTextField(medecin.getMedNumAgreement());

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

        int option = JOptionPane.showConfirmDialog(null, fields, "Modifier un Médecin", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();

            // Vérification de l'email
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "L'adresse email est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            medecin.setUtiNom(nomField.getText());
            medecin.setUtiPrenom(prenomField.getText());
            medecin.setAdresse(new Adresse(null, adresseField.getText(), codePostalField.getText(), villeField.getText()));
            medecin.setUtiTel(telephoneField.getText());
            medecin.setUtiEmail(email);
            medecin.setMedNumAgreement(numeroAgrementField.getText());

            boolean success = medecinDAO.update(medecin);
            if (success) {
                updateMedecinComboBox();
                JOptionPane.showMessageDialog(this, "Médecin modifié avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification du médecin.");
            }
        }
    }

    private void supprimerMedecin() {
        int selectedIndex = medecinComboBox.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médecin à supprimer.");
            return;
        }

        Medecin medecin = medecins.get(selectedIndex);
        int option = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer le médecin sélectionné ?", "Confirmation de suppression", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            boolean success = medecinDAO.delete(medecin.getUtiId());
            if (success) {
                updateMedecinComboBox();
                detailsArea.setText("");
                JOptionPane.showMessageDialog(this, "Médecin supprimé avec succès.");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du médecin.");
            }
        }
    }

    private void rechercherMedecin() {
        String nomRecherche = JOptionPane.showInputDialog(this, "Entrez le nom du médecin à rechercher :");
        if (nomRecherche != null && !nomRecherche.trim().isEmpty()) {
            for (int i = 0; i < medecinComboBox.getItemCount(); i++) {
                if (medecinComboBox.getItemAt(i).toLowerCase().contains(nomRecherche.toLowerCase())) {
                    medecinComboBox.setSelectedIndex(i);
                    afficherDetailsMedecin();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Médecin non trouvé.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
}
