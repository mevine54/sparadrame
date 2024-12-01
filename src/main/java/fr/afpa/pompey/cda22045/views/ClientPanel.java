package fr.afpa.pompey.cda22045.views;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ClientPanel extends JPanel {
    private Map<String, String> clients;

    public ClientPanel() {
        clients = new HashMap<>();
        clients.put("Dupont Jean", "1 rue de la Paix 75000 Paris, Tel: 0123456789");
        clients.put("Martin Marie", "2 avenue des Champs 75008 Paris, Tel: 0987654321");

        setBackground(new Color(144, 238, 144));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Gestion des clients:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> clientComboBox = new JComboBox<>(clients.keySet().toArray(new String[0]));
        add(clientComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JTextArea clientDetails = new JTextArea(5, 20);
        clientDetails.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(clientDetails);
        add(scrollPane, gbc);

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

        clientComboBox.addActionListener(e -> {
            String selectedClient = clientComboBox.getSelectedItem().toString();
            clientDetails.setText(clients.getOrDefault(selectedClient, "Aucune information disponible"));
        });

        btnRetour.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());
    }
}
