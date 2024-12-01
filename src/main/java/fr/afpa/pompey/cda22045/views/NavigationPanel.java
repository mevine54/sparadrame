package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.DashboardView;
import fr.afpa.pompey.cda22045.controllers.HistoriqueController;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JPanel {
    private DashboardView dashboardView;
    private HistoriqueController historiqueController;

    public NavigationPanel(DashboardView dashboardView, HistoriqueController historiqueController) {
        this.dashboardView = dashboardView;
        this.historiqueController = historiqueController;

        setLayout(new GridLayout(6, 1, 10, 10));
        setBackground(new Color(85, 170, 85));

        // Buttons for navigation
        JButton btnAchat = new JButton("Effectuer un achat");
        JButton btnHistorique = new JButton("Consulter l'historique des achats");
        JButton btnMedecin = new JButton("Consulter un médecin / spécialiste");
        JButton btnClient = new JButton("Consulter un client");
        JButton btnQuitter = new JButton("Quitter l'application");

        // Adding buttons to the panel
        add(btnAchat);
        add(btnHistorique);
        add(btnMedecin);
        add(btnClient);
        add(btnQuitter);

        // Action Listeners for buttons
        btnAchat.addActionListener(e -> dashboardView.updateMainPanel(new AchatPanel(historiqueController)));
        btnHistorique.addActionListener(e -> dashboardView.updateMainPanel(new HistoriquePanel(historiqueController)));
        btnMedecin.addActionListener(e -> dashboardView.updateMainPanel(new MedecinPanel()));
        btnClient.addActionListener(e -> dashboardView.updateMainPanel(new ClientPanel()));
        btnQuitter.addActionListener(e -> System.exit(0));
    }
}
