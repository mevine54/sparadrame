package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.controllers.HistoriqueController;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AchatPanel extends JPanel {
    private HistoriqueController historiqueController;

    private List<String> historiqueAchats;

    public AchatPanel(HistoriqueController historiqueController) {
        this.historiqueController = historiqueController;
        historiqueAchats = new ArrayList<>();

        setBackground(new Color(144, 238, 144));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel achatLabel = new JLabel("Achat direct sélectionné");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(achatLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 1;
        add(new JLabel("Sélectionner le type d'achat:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> typeAchat = new JComboBox<>(new String[]{"Achat direct", "Achat via ordonnance"});
        add(typeAchat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Sélectionner un médicament:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> medicament = new JComboBox<>(new String[]{"Paracétamol", "Ibuprofène"});
        add(medicament, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Prix unitaire:"), gbc);
        gbc.gridx = 1;
        JTextField prixUnitaire = new JTextField("5.00", 10);
        prixUnitaire.setEditable(false);
        add(prixUnitaire, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Quantité:"), gbc);
        gbc.gridx = 1;
        JTextField quantite = new JTextField(10);
        add(quantite, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Prix total:"), gbc);
        gbc.gridx = 1;
        JTextField prixTotal = new JTextField(10);
        prixTotal.setEditable(false);
        add(prixTotal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        JButton btnAjouterMedicament = new JButton("Ajouter médicament");
        add(btnAjouterMedicament, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        JButton btnValiderAchat = new JButton("Valider l'achat");
        add(btnValiderAchat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JTextArea medicamentsAjoutes = new JTextArea(5, 20);
        medicamentsAjoutes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(medicamentsAjoutes);
        add(new JLabel("Médicaments ajoutés:"), gbc);
        gbc.gridy = 8;
        add(scrollPane, gbc);

        gbc.gridy = 9;
        JButton btnRetour = new JButton("Retour");
        add(btnRetour, gbc);

        // Add additional fields for Achat via ordonnance
        gbc.gridy = 10;
        gbc.gridx = 0;
        JLabel medecinLabel = new JLabel("Sélectionner un médecin traitant:");
        add(medecinLabel, gbc);
        gbc.gridx = 1;
        JComboBox<String> medecinComboBox = new JComboBox<>(new String[]{"Dr. Durand", "Dr. Martin"});
        add(medecinComboBox, gbc);

        gbc.gridy = 11;
        gbc.gridx = 0;
        JLabel clientLabel = new JLabel("Sélectionner un client:");
        add(clientLabel, gbc);
        gbc.gridx = 1;
        JComboBox<String> clientComboBox = new JComboBox<>(new String[]{"Dupont Jean", "Martin Marie"});
        add(clientComboBox, gbc);

        // Initially hide ordonnance-specific fields
        medecinLabel.setVisible(false);
        medecinComboBox.setVisible(false);
        clientLabel.setVisible(false);
        clientComboBox.setVisible(false);

        // Action Listeners
        typeAchat.addActionListener(e -> {
            if (typeAchat.getSelectedItem().toString().equals("Achat via ordonnance")) {
                achatLabel.setText("Achat via ordonnance sélectionné");
                medecinLabel.setVisible(true);
                medecinComboBox.setVisible(true);
                clientLabel.setVisible(true);
                clientComboBox.setVisible(true);
            } else {
                achatLabel.setText("Achat direct sélectionné");
                medecinLabel.setVisible(false);
                medecinComboBox.setVisible(false);
                clientLabel.setVisible(false);
                clientComboBox.setVisible(false);
            }
        });

        btnAjouterMedicament.addActionListener(e -> {
            String med = medicament.getSelectedItem().toString();
            String qty = quantite.getText();
            if (!qty.isEmpty() && qty.matches("\\d+")) {
                double total = Double.parseDouble(prixUnitaire.getText()) * Integer.parseInt(qty);
                prixTotal.setText(String.valueOf(total));
                String achatDetails = med + " - Quantité: " + qty + " - Prix Total: " + total;
                medicamentsAjoutes.append(achatDetails + "\n");
                historiqueAchats.add(achatDetails);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une quantité valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        quantite.addActionListener(e -> {
            btnAjouterMedicament.doClick();
        });

        btnValiderAchat.addActionListener(e -> {
            if (!historiqueAchats.isEmpty()) {
                HistoriquePanel.addHistorique(historiqueController, historiqueAchats);
                JOptionPane.showMessageDialog(this, "Achat validé avec succès !", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                historiqueAchats.clear();
                medicamentsAjoutes.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins un médicament.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRetour.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());
    }
}

