package fr.afpa.pompey.cda22045.views;

import javax.swing.*;
import fr.afpa.pompey.cda22045.utilities.SessionManager;

public class LoginSystemGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Connexion utilisateur");
        JTextField userIdField = new JTextField();
        JButton loginButton = new JButton("Se connecter");
        JLabel statusLabel = new JLabel("Veuillez entrer votre ID utilisateur.");

        frame.setLayout(new java.awt.GridLayout(3, 1));
        frame.add(userIdField);
        frame.add(loginButton);
        frame.add(statusLabel);

        loginButton.addActionListener(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                if (SessionManager.loginUser(userId)) {
                    statusLabel.setText("Connexion réussie pour l'utilisateur ID : " + userId);
                } else {
                    statusLabel.setText("Échec de la connexion.");
                }
            } catch (NumberFormatException ex) {
                statusLabel.setText("Veuillez entrer un ID valide.");
            }
        });

        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

