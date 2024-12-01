package fr.afpa.pompey.cda22045;

import javax.swing.*;
import java.awt.*;
import fr.afpa.pompey.cda22045.controllers.HistoriqueController;
import fr.afpa.pompey.cda22045.views.NavigationPanel;
import fr.afpa.pompey.cda22045.views.WelcomePanel;

public class DashboardView extends JFrame {
    private HistoriqueController historiqueController;

    public DashboardView() {
        historiqueController = new HistoriqueController(); // Créer une instance de HistoriqueController

        setTitle("Pharmacie Sparadrap - Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Créer une instance de NavigationPanel avec les bons arguments
        NavigationPanel navigationPanel = new NavigationPanel(this, historiqueController);
        add(navigationPanel, BorderLayout.WEST);

        // Ajouter le panneau d'accueil initial
        JPanel mainPanel = new WelcomePanel();
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Méthode pour mettre à jour le panneau principal
    public void updateMainPanel(JPanel newPanel) {
        getContentPane().remove(1); // Supprimer le panneau principal actuel
        add(newPanel, BorderLayout.CENTER); // Ajouter le nouveau panneau
        revalidate();
        repaint();
    }

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardView dashboard = new DashboardView();
            dashboard.setVisible(true);
        });
    }
}
