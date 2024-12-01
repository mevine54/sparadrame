package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.controllers.HistoriqueController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoriquePanel extends JPanel {
    private HistoriqueController historiqueController;
    private JTable table;

    public HistoriquePanel(HistoriqueController controller) {
        this.historiqueController = controller;
        setBackground(new Color(144, 238, 144));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Historique des achats", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.NORTH);

        String[] columnNames = {"Détails de l'achat"};
        updateTableData();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnModifier = new JButton("Modifier l'achat sélectionné");
        bottomPanel.add(btnModifier);

        JButton btnRetour = new JButton("Retour");
        bottomPanel.add(btnRetour);

        add(bottomPanel, BorderLayout.SOUTH);

        btnModifier.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String selectedAchat = historiqueController.getHistoriqueData().get(selectedRow);
                String[] parts = selectedAchat.split(" - Quantité: | - Prix Total: ");
                if (parts.length >= 3) {
                    String newQuantityStr = JOptionPane.showInputDialog(this, "Modifier la quantité:", parts[1]);
                    try {
                        int newQuantity = Integer.parseInt(newQuantityStr.trim());
                        historiqueController.modifyAchat(selectedRow, newQuantity);
                        updateTableData();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Quantité invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un achat à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRetour.addActionListener(e -> {
            Container parent = SwingUtilities.getAncestorOfClass(JFrame.class, this);
            if (parent != null) {
                ((JFrame) parent).setContentPane(new WelcomePanel());
                ((JFrame) parent).revalidate();
                ((JFrame) parent).repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Impossible de revenir en arrière.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void updateTableData() {
        String[] columnNames = {"Détails de l'achat"};
        List<String> historiqueData = historiqueController.getHistoriqueData();
        Object[][] data = new Object[historiqueData.size()][1];
        for (int i = 0; i < historiqueData.size(); i++) {
            data[i][0] = historiqueData.get(i);
        }
        if (table == null) {
            table = new JTable(data, columnNames);
        } else {
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        }
    }

    public static void addHistorique(HistoriqueController controller, List<String> achats) {
        for (String achat : achats) {
            if (!achat.isEmpty()) {
                controller.addAchat(achat, false); // Modification : empêcher le regroupement automatique
            }
        }
    }
}







