package fr.afpa.pompey.cda22045.views;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Bienvenue dans la Pharmacie Sparadrap", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        JLabel imageLabel = new JLabel(new ImageIcon("/path/to/image.jpg"));
        add(imageLabel, BorderLayout.CENTER);
    }
}

