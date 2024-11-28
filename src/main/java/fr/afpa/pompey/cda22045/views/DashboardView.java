package fr.afpa.pompey.cda22045.views;

import fr.afpa.pompey.cda22045.dao.ClientDAO;
import fr.afpa.pompey.cda22045.dao.MedecinDAO;
import fr.afpa.pompey.cda22045.dao.MedicamentDAO;
import fr.afpa.pompey.cda22045.dao.MutuelleDAO;
import fr.afpa.pompey.cda22045.models.*;
import fr.afpa.pompey.cda22045.enums.enumTypeMedicament;
import fr.afpa.pompey.cda22045.enums.enumTypeSpecialite;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.ValidationUtils.*;

public class DashboardView extends JFrame {

    private MutuelleDAO mutuelleDAO = new MutuelleDAO();
    private MedecinDAO medecinDAO = new MedecinDAO();
    private MedicamentDAO medicamentDAO = new MedicamentDAO();
    private JPanel panelCentral;
    private DefaultTableModel tableModel; // Modèle de table pour l'historique
    private List<Object[]> achatsHistorique = new ArrayList<>(); // Liste pour stocker les achats
    private List<Medicament> listeMedicamentsAjoutes = new ArrayList<>();
    private List<Achat> achatsValides = new ArrayList<>();
    private JTextArea detailsClientArea;
    private ClientDAO clientDAO = new ClientDAO();
    private JComboBox<Client> clientCombo = new JComboBox<>();
    private JComboBox<TypeSpecialite> specialiteCombo = new JComboBox<>();
    private List<Medecin> listeMedecins = new ArrayList<>();
    private List<Medicament> listeMedicaments = new ArrayList<>();


    // Liste globale des clients
    private List<Client> listeClients = new ArrayList<>();

//    private ArrayList<Client> listeClients = (ArrayList<Client>) clientDAO.getAll();

    // Méthode pour obtenir la liste des clients sous forme de tableau (pour l'utiliser dans JComboBox)
//    private Client[] getListeClients() {
//        return listeClients.toArray(new Client[0]);
//    }

    // Méthode pour ajouter un nouveau client à la liste globale
    private void ajouterNouveauClient(Client client) {
        listeClients.add(client);
    }

    // Méthode pour supprimer un client de la liste globale
    private void supprimerClient(Client client) {
        listeClients.remove(client);
    }

    public DashboardView() {
        // Paramètres de base de la fenêtre
        setTitle("Pharmacie Sparadrap - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Disposition principale
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Panneau central
        panelCentral = new JPanel(new CardLayout());
        add(panelCentral, BorderLayout.CENTER);

        // Initialisation de la liste des clients et des médecins
        try {

            listeClients = clientDAO.getAll();
            listeMedecins = medecinDAO.getAll();
            listeMedicaments = medicamentDAO.getAll();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        // Page d'accueil au démarrage
        revenirAccueil();

        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color vertClair = new Color(85, 170, 85);
                Color vertFonce = new Color(34, 139, 34);
                GradientPaint gradient = new GradientPaint(0, 0, vertFonce, 0, getHeight(), vertClair);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(true);

        JButton btnAchat = createVisibleGradientButton("Effectuer un achat");
        JButton btnHistorique = createVisibleGradientButton("Consulter l'historique des achats");
        JButton btnMedecin = createVisibleGradientButton("Consulter un médecin / spécialiste");
        JButton btnClient = createVisibleGradientButton("Consulter un client");
        JButton btnQuitter = createVisibleGradientButton("Quitter l'application");

        sidebar.add(btnAchat);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnHistorique);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnMedecin);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnClient);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnQuitter);

        btnAchat.addActionListener(e -> afficherAchatPanel());
        btnHistorique.addActionListener(e -> afficherHistoriquePanel());
        btnMedecin.addActionListener(e -> afficherMedecinPanel());
        btnClient.addActionListener(e -> afficherClientPanel());
        btnQuitter.addActionListener(e -> System.exit(0));

        return sidebar;
    }

    private JButton createVisibleGradientButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color vertClair = new Color(144, 238, 144);
                Color vertFonce = new Color(34, 139, 34);
                GradientPaint gradient = new GradientPaint(0, 0, vertClair, getWidth(), getHeight(), vertFonce);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        return button;
    }

    // Page d'achat restaurée avec tout le contenu
    private void afficherAchatPanel() {
        // Réinitialiser les listes de médicaments ajoutés et d'achats valides
        resetAchatState();

        panelCentral.removeAll();

        // Panneau pour l'achat
        JPanel panelAchat = new JPanel();
        panelAchat.setLayout(new BoxLayout(panelAchat, BoxLayout.Y_AXIS));
        panelAchat.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelAchat.setBackground(new Color(230, 255, 230)); // Couleur vert très clair

        // Label dynamique pour indiquer le type d'achat avec une plus grande police
        JLabel labelAchatType = new JLabel("Achat direct sélectionné");
        labelAchatType.setFont(new Font("Arial", Font.BOLD, 18));  // Augmenter la taille de la police

        // Label pour le type d'achat
        JLabel labelTypeAchat = new JLabel("Sélectionner le type d'achat:");
        String[] typesAchat = {"Achat direct", "Achat via ordonnance"};
        JComboBox<String> typeAchatCombo = new JComboBox<>(typesAchat);

        // Sélection des médicaments
        JLabel labelMedicament = new JLabel("Sélectionner un médicament:");
//        TypeMedicament[] medicaments = Arrays.stream(enumTypeMedicament.values())
//                .map(TypeMedicament::fromEnum)
//                .toArray(TypeMedicament[]::new);
//        JComboBox<TypeMedicament> medicamentCombo = new JComboBox<>(medicaments);


        DefaultComboBoxModel<Medicament> comboBoxMedicamentModel = new DefaultComboBoxModel<>(listeMedicaments.toArray(new Medicament[0]));
        JComboBox<Medicament> medicamentCombo = new JComboBox<>(comboBoxMedicamentModel);

        // Prix unitaire et quantité
        JLabel labelPrixUnitaire = new JLabel("Prix unitaire:");
        JTextField prixUnitaireField = new JTextField("5.00", 10);
        prixUnitaireField.setEditable(false);

        JLabel labelQuantite = new JLabel("Quantité:");
        JTextField quantiteField = new JTextField("1", 10);

        // Prix total
        JLabel labelPrixTotal = new JLabel("Prix total:");
        JTextField prixTotalField = new JTextField("5.00", 10);
        prixTotalField.setEditable(false);

        // Ajout d'un écouteur pour mettre à jour le prix total en fonction de la quantité
        quantiteField.addActionListener(e -> {
            if (isValidQuantity(quantiteField.getText())) {
                double prixUnitaire = Double.parseDouble(prixUnitaireField.getText());
                int quantite = Integer.parseInt(quantiteField.getText());
                double prixTotal = prixUnitaire * quantite;
                prixTotalField.setText(String.format("%.2f", prixTotal));
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une quantité valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Liste des médicaments ajoutés
        JLabel labelMedicamentsAjoutes = new JLabel("Médicaments ajoutés:");
        JTextArea medicamentsAjoutesArea = new JTextArea(5, 20);
        medicamentsAjoutesArea.setEditable(false);

        // Bouton pour ajouter le médicament
        JButton btnAjouterMedicament = new JButton("Ajouter médicament");
        btnAjouterMedicament.addActionListener(e -> {
//            TypeMedicament selectedMedicament = (TypeMedicament) medicamentCombo.getSelectedItem();
            Medicament selectedMedicament = (Medicament) medicamentCombo.getSelectedItem();
            String quantite = quantiteField.getText();
            if (isValidQuantity(quantite)) {
                double prixUnitaire = Double.parseDouble(prixUnitaireField.getText());
                double prixTotal = prixUnitaire * Integer.parseInt(quantite);
//                String medicamentDetail = selectedMedicament.getTmTypeNom() + " (x" + quantite + ") - Prix: " + prixTotal + " €";
                listeMedicamentsAjoutes.add(selectedMedicament);

                // Mise à jour de la textArea
                StringBuilder sb = new StringBuilder();
                for (Medicament listeMedicamentsAjoute : listeMedicamentsAjoutes) {
                    sb.append(listeMedicamentsAjoute.toString() + "\n");
                }
                medicamentsAjoutesArea.setText(sb.toString());

                // Stocker les achats validés
//                Object[] achat = {selectedMedicament, quantite, prixTotal};
                String typeAchat = typeAchatCombo.getSelectedItem().toString();
//                if ( typeAchat.equals("Ordonnance")){
//                    Achat achat = new Achat(null,);
//                }
//                else{
//                    Achat achat = new Achat(null,);
//                }
                Client selectedClient = (Client) clientCombo.getSelectedItem();
                Achat achat = new Achat(null,typeAchat,LocalDate.now(), selectedClient);

                achatsValides.add(achat);

                // Remettre le champ quantité à 1 et mettre à jour le prix total
                quantiteField.setText("1");
                prixTotalField.setText("5.00");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une quantité valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ajout dynamique pour le choix du médecin et du client si "Achat via ordonnance"
        JLabel labelMedecin = new JLabel("Sélectionner un médecin traitant:");
        TypeSpecialite[] specialites = Arrays.stream(enumTypeSpecialite.values())
                .map(TypeSpecialite::fromEnum)
                .toArray(TypeSpecialite[]::new);
        JComboBox<TypeSpecialite> medecinCombo = new JComboBox<>(specialites);
        JLabel labelClient = new JLabel("Sélectionner un client:");
        DefaultComboBoxModel<Client> comboBoxModel = new DefaultComboBoxModel<>(listeClients.toArray(new Client[0]));
        clientCombo = new JComboBox<>(comboBoxModel);

        // Par défaut, on cache les champs médecin et client
        labelMedecin.setVisible(false);
        medecinCombo.setVisible(false);
        labelClient.setVisible(false);
        clientCombo.setVisible(false);

        // Gérer le changement de type d'achat
        typeAchatCombo.addActionListener(e -> {
            String typeSelectionne = (String) typeAchatCombo.getSelectedItem();
            if ("Achat via ordonnance".equals(typeSelectionne)) {
                labelAchatType.setText("Achat via ordonnance sélectionné");
                labelMedecin.setVisible(true);
                medecinCombo.setVisible(true);
                labelClient.setVisible(true);
                clientCombo.setVisible(true);
            } else {
                labelAchatType.setText("Achat direct sélectionné");
                labelMedecin.setVisible(false);
                medecinCombo.setVisible(false);
                labelClient.setVisible(false);  // Cacher le champ client pour achat direct
            }
        });

        // Bouton retour et validation
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> revenirAccueil());

        JButton btnValiderAchat = new JButton("Valider l'achat");
        // Ajout d'une condition pour ne pas afficher le médecin pour les achats directs dans la méthode `afficherAchatPanel()`

        btnValiderAchat.addActionListener(e -> {
            if (achatsValides.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez ajouter des médicaments avant de valider l'achat.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Votre achat a bien été pris en compte !");

            // Déterminer si l'achat est avec ou sans ordonnance
            String typeAchat = (String) typeAchatCombo.getSelectedItem();
            String medecinSelectionne = "";
            String clientSelectionne = "";

            if ("Achat via ordonnance".equals(typeAchat)) {
                // Si c'est un achat avec ordonnance, récupérer le médecin et le client
                medecinSelectionne = ((TypeSpecialite) medecinCombo.getSelectedItem()).getTsTypeNom();
                clientSelectionne = (String) clientCombo.getSelectedItem();
            }

            // Ajouter l'achat à l'historique

            for (Achat achatsValide  : achatsValides) {
                Object[] achat = new Object[0];
                Object[] achatHistorique = {
                        "13/10/2024",
                        "Achat direct".equals(typeAchat) ? "" : clientSelectionne, // Nom du client pour achat direct est vide
                        achat[0], // Médicament
                        achat[1], // Quantité
                        String.format("%.2f", achat[2]), // Prix total
                        "Achat direct".equals(typeAchat) ? "" : medecinSelectionne // Nom du médecin pour achat direct est vide
                };
                achatsHistorique.add(achatHistorique); // Ajouter à la liste d'historique
            }

            revenirAccueil();  // Retourner à l'accueil après validation
        });

        // Ajout des composants au panel d'achat
        panelAchat.add(labelAchatType);  // Libellé indicatif pour le type d'achat
        panelAchat.add(labelTypeAchat);
        panelAchat.add(typeAchatCombo);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelMedicament);
        panelAchat.add(medicamentCombo);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelPrixUnitaire);
        panelAchat.add(prixUnitaireField);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelQuantite);
        panelAchat.add(quantiteField);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelPrixTotal);
        panelAchat.add(prixTotalField);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(btnAjouterMedicament);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelMedicamentsAjoutes);
        panelAchat.add(new JScrollPane(medicamentsAjoutesArea));
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(labelMedecin);  // Médecin pour achat avec ordonnance
        panelAchat.add(medecinCombo);
        panelAchat.add(labelClient);   // Client pour achat avec ordonnance
        panelAchat.add(clientCombo);
        panelAchat.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAchat.add(btnValiderAchat);
        panelAchat.add(btnRetour);

        // Remplacer le contenu du panneau central
        panelCentral.add(panelAchat);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Méthode pour réinitialiser les listes de médicaments ajoutés et d'achats valides
    private void resetAchatState() {
        listeMedicamentsAjoutes.clear();
        achatsValides.clear();
    }

    // Page d'historique des achats avec possibilité de modifier
    private void afficherHistoriquePanel() {
        panelCentral.removeAll();

        // Panneau pour l'historique des achats
        JPanel panelHistorique = new JPanel();
        panelHistorique.setLayout(new BoxLayout(panelHistorique, BoxLayout.Y_AXIS));
        panelHistorique.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelHistorique.setBackground(new Color(230, 255, 230)); // Appliquer la même couleur que la page achat

        // Label pour le titre
        JLabel labelHistorique = new JLabel("Historique des achats");
        labelHistorique.setFont(new Font("Arial", Font.BOLD, 18));

        // Tableau pour afficher les achats avec les colonnes supplémentaires
        String[] colonnes = {"Date", "Client", "Médicament", "Quantité", "Prix total", "Médecin"};
        Object[][] achats = achatsHistorique.toArray(new Object[0][]);

        tableModel = new DefaultTableModel(achats, colonnes);
        JTable tableAchats = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAchats);

        // Bouton pour modifier l'achat sélectionné
        JButton btnModifierAchat = new JButton("Modifier l'achat sélectionné");
        // Ajout de la fonctionnalité de modification du médecin et du client
        btnModifierAchat.addActionListener(e -> {
            int selectedRow = tableAchats.getSelectedRow();
            if (selectedRow != -1) {
                // Ouvre une boîte de dialogue pour modifier le médicament, la quantité, le médecin et le client
                String date = (String) tableAchats.getValueAt(selectedRow, 0);
                String client = (String) tableAchats.getValueAt(selectedRow, 1);
                TypeMedicament medicament = (TypeMedicament) tableAchats.getValueAt(selectedRow, 2);
                String quantite = (String) tableAchats.getValueAt(selectedRow, 3);
                TypeSpecialite medecin = (TypeSpecialite) tableAchats.getValueAt(selectedRow, 5);

                double prixUnitaire = 5.00; // Prix unitaire par défaut (tu peux l'adapter si nécessaire)

                JPanel modificationPanel = new JPanel(new GridLayout(4, 2));
                modificationPanel.add(new JLabel("Médicament:"));
                JComboBox<TypeMedicament> medicamentField = new JComboBox<>(Arrays.stream(enumTypeMedicament.values())
                        .map(TypeMedicament::fromEnum)
                        .toArray(TypeMedicament[]::new));
                medicamentField.setSelectedItem(medicament);
                modificationPanel.add(medicamentField);

                modificationPanel.add(new JLabel("Quantité:"));
                JTextField quantiteField = new JTextField(quantite);
                modificationPanel.add(quantiteField);

                modificationPanel.add(new JLabel("Client:"));
//                String[] clientsAvecAucun = addOptionAucun(listeClients);
//                JComboBox<String> clientCombo = new JComboBox<>(clientsAvecAucun);
//                clientCombo.setSelectedItem(client != null && !client.isEmpty() ? client : "Aucun");  // Sélectionner "Aucun" si le client est vide
//                modificationPanel.add(clientCombo);

                modificationPanel.add(new JLabel("Médecin:"));
                JComboBox<TypeSpecialite> medecinCombo = new JComboBox<>(Arrays.stream(enumTypeSpecialite.values())
                        .map(TypeSpecialite::fromEnum)
                        .toArray(TypeSpecialite[]::new));
                medecinCombo.setSelectedItem(medecin != null ? medecin : TypeSpecialite.fromEnum(enumTypeSpecialite.CARDIOLOGIE));  // Sélectionner "Aucun" si le médecin est vide
                modificationPanel.add(medecinCombo);

                int result = JOptionPane.showConfirmDialog(this, modificationPanel, "Modifier l'achat",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Parcourir toutes les lignes de la table pour mettre à jour uniquement les lignes de l'achat sélectionné
                    String nouveauClient = (String) clientCombo.getSelectedItem();
                    TypeSpecialite nouveauMedecin = (TypeSpecialite) medecinCombo.getSelectedItem();
                    TypeMedicament nouveauMedicament = (TypeMedicament) medicamentField.getSelectedItem();
                    String nouvelleQuantite = quantiteField.getText();

                    if (isValidQuantity(nouvelleQuantite)) {
                        // Mettre à jour les lignes qui correspondent à l'achat sélectionné (basé sur la date, le client et le médicament)
                        for (int i = 0; i < tableAchats.getRowCount(); i++) {
                            String currentDate = (String) tableAchats.getValueAt(i, 0);
                            String currentClient = (String) tableAchats.getValueAt(i, 1);
                            TypeMedicament currentMedicament = (TypeMedicament) tableAchats.getValueAt(i, 2);

                            if (currentDate.equals(date) && currentClient.equals(client) && currentMedicament.equals(medicament)) {
                                // Mettre à jour uniquement cette ligne
                                tableAchats.setValueAt(nouveauClient.equals("Aucun") ? "" : nouveauClient, i, 1);
                                tableAchats.setValueAt(nouveauMedecin.equals("Aucun") ? "" : nouveauMedecin, i, 5);
                                tableAchats.setValueAt(nouveauMedicament, i, 2);
                                tableAchats.setValueAt(nouvelleQuantite, i, 3);

                                // Recalculer le prix total basé sur la nouvelle quantité
                                int nouvelleQuantiteInt = Integer.parseInt(nouvelleQuantite);
                                double nouveauPrixTotal = prixUnitaire * nouvelleQuantiteInt;
                                tableAchats.setValueAt(String.format("%.2f", nouveauPrixTotal), i, 4);
                            }
                        }

                        JOptionPane.showMessageDialog(this, "L'achat a été modifié !");
                    } else {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer une quantité valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un achat à modifier.");
            }
        });

        // Bouton retour et quitter
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> revenirAccueil());

        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(e -> System.exit(0));

        // Ajout des composants au panneau d'historique
        panelHistorique.add(labelHistorique);
        panelHistorique.add(Box.createRigidArea(new Dimension(0, 10)));
        panelHistorique.add(scrollPane);
        panelHistorique.add(Box.createRigidArea(new Dimension(0, 10)));
        panelHistorique.add(btnModifierAchat);
        panelHistorique.add(Box.createRigidArea(new Dimension(0, 10)));
        panelHistorique.add(btnRetour);
        panelHistorique.add(btnQuitter);

        // Remplacer le contenu du panneau central
        panelCentral.add(panelHistorique);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Méthode pour revenir à la page d'accueil
    private void revenirAccueil() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Bienvenue dans la Pharmacie Sparadrap", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        // Charger l'image
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/caducv.jpg"));
        if (imageIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement de l'image",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            JLabel imageLabel = new JLabel(imageIcon);
            panel.add(imageLabel, BorderLayout.CENTER);
        }

        // Remplacer le contenu du panneau central
        panelCentral.removeAll();
        panelCentral.add(panel);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Page pour afficher les détails d'un médecin / spécialiste
    private void afficherMedecinPanel() {
        panelCentral.removeAll();

        // Panneau pour afficher les détails d'un médecin / spécialiste
        JPanel panelMedecin = new JPanel(new GridBagLayout());
        panelMedecin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelMedecin.setBackground(new Color(230, 255, 230)); // Appliquer la même couleur que la page achat

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Label pour le titre
        JLabel labelMedecin = new JLabel("Détails des médecins / spécialistes");
        labelMedecin.setFont(new Font("Arial", Font.BOLD, 18));
        panelMedecin.add(labelMedecin, gbc);

        gbc.gridy++;
        // Liste déroulante pour sélectionner un médecin
        JLabel labelSelectMedecin = new JLabel("Sélectionner un médecin:");
        panelMedecin.add(labelSelectMedecin, gbc);

        gbc.gridy++;
//        JComboBox<Medecin> medecinCombo = new JComboBox<>(getListeMedecins());
//        JComboBox<Medecin> medecinCombo = new JComboBox<>( listeMedecins);
        DefaultComboBoxModel<Medecin> comboBoxModelMedecin = new DefaultComboBoxModel<>(listeMedecins.toArray(new Medecin[0]));
        JComboBox<Medecin> medecinCombo = new JComboBox<>(comboBoxModelMedecin);
//        medecinCombo.setRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                if (value instanceof TypeSpecialite) {
//                    TypeSpecialite medecin = (TypeSpecialite) value;
//                    setText(medecin.getTsTypeNom());
//                }
//                return this;
//            }
//        });
        panelMedecin.add(medecinCombo, gbc);

        gbc.gridy++;
        // Détails du médecin (affichage)
        JTextArea detailsMedecinArea = new JTextArea(5, 30);
        detailsMedecinArea.setEditable(false);
        panelMedecin.add(new JScrollPane(detailsMedecinArea), gbc);

        // Afficher les informations du médecin sélectionné
        medecinCombo.addActionListener(e -> {
            TypeSpecialite medecinSelectionne = (TypeSpecialite) medecinCombo.getSelectedItem();
            if (medecinSelectionne != null) {
                detailsMedecinArea.setText("Détails du médecin: " + medecinSelectionne.getTsTypeNom() + "\nAdresse: Exemple Adresse\nTéléphone: 0123456789\nEmail: exemple@gmail.com");
            } else {
                detailsMedecinArea.setText("");
            }
        });

        // Taille uniforme pour les boutons
        Dimension buttonSize = new Dimension(150, 30);

        gbc.gridy++;
        gbc.gridwidth = 1;
        // Bouton pour créer un nouveau médecin
        JButton btnCreer = new JButton("Créer");
        btnCreer.setPreferredSize(buttonSize);
        btnCreer.setMaximumSize(buttonSize);
        btnCreer.addActionListener(e -> {
            JPanel nouveauMedecinPanel = new JPanel(new GridLayout(7, 2));
            nouveauMedecinPanel.add(new JLabel("Nom:"));
            JTextField nomField = new JTextField();
            nouveauMedecinPanel.add(nomField);
            nouveauMedecinPanel.add(new JLabel("Prénom:"));
            JTextField prenomField = new JTextField();
            nouveauMedecinPanel.add(prenomField);
            nouveauMedecinPanel.add(new JLabel("Spécialité:"));
            JTextField specialiteField = new JTextField();
            nouveauMedecinPanel.add(specialiteField);
            nouveauMedecinPanel.add(new JLabel("Adresse:"));
            JTextField adresseField = new JTextField();
            nouveauMedecinPanel.add(adresseField);
            nouveauMedecinPanel.add(new JLabel("Téléphone:"));
            JTextField telField = new JTextField();
            nouveauMedecinPanel.add(telField);
            nouveauMedecinPanel.add(new JLabel("Email:"));
            JTextField emailField = new JTextField();
            nouveauMedecinPanel.add(emailField);
            nouveauMedecinPanel.add(new JLabel("Numéro d'Agréement:"));
            JTextField medNumAgreementField = new JTextField();
            nouveauMedecinPanel.add(medNumAgreementField);
            int result = JOptionPane.showConfirmDialog(this, nouveauMedecinPanel, "Créer un nouveau médecin", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String specialite = specialiteField.getText();
                    String adresse = adresseField.getText();
                    String telephone = telField.getText();
                    String email = emailField.getText();
                    String medNumAgreement = medNumAgreementField.getText();

                    if (isValidName(nom) && isValidName(prenom) && isValidPhoneNumber(telephone) && isValidEmail(email)) {
//                        TypeSpecialite nouveauMedecin = new TypeSpecialite(
//                                nom,
//                                prenom,
//                                specialite,
//                                adresse,
//                                telephone,
//                                email
//                        );
                        Medecin nouveauMedecin = new Medecin(
                                null,
                                nom,
                                prenom,
                                null,
                                telephone,
                                email,
                                medNumAgreement
                        );
                        medecinCombo.addItem(nouveauMedecin);
                        JOptionPane.showMessageDialog(this, "Nouveau médecin créé !");
                    } else {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer des informations valides pour le médecin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelMedecin.add(btnCreer, gbc);

        gbc.gridx++;
        // Bouton pour modifier un médecin
        JButton btnModifier = new JButton("Modifier");
        btnModifier.setPreferredSize(buttonSize);
        btnModifier.setMaximumSize(buttonSize);
        btnModifier.addActionListener(e -> {
//            TypeSpecialite medecinSelectionne = (TypeSpecialite) medecinCombo.getSelectedItem();
            Medecin medecinSelectionne = (Medecin) medecinCombo.getSelectedItem();

            if (medecinSelectionne == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médecin à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel modificationMedecinPanel = new JPanel(new GridLayout(6, 2));
            modificationMedecinPanel.add(new JLabel("Nom:"));
            JTextField nomField = new JTextField(medecinSelectionne.getNom());
            modificationMedecinPanel.add(nomField);
            modificationMedecinPanel.add(new JLabel("Prénom:"));
            JTextField prenomField = new JTextField(medecinSelectionne.getPrenom());
            modificationMedecinPanel.add(prenomField);
            modificationMedecinPanel.add(new JLabel("Spécialité:"));
//            JTextField specialiteField = new JTextField(medecinSelectionne.getTsTypeNom());
//            modificationMedecinPanel.add(specialiteField);
            modificationMedecinPanel.add(new JLabel("Adresse:"));
            JTextField adresseField = new JTextField(medecinSelectionne.getAdresse().toString());
            modificationMedecinPanel.add(adresseField);
            modificationMedecinPanel.add(new JLabel("Téléphone:"));
            JTextField telField = new JTextField(medecinSelectionne.getTelephone());
            modificationMedecinPanel.add(telField);
            modificationMedecinPanel.add(new JLabel("Email:"));
            JTextField emailField = new JTextField(medecinSelectionne.getEmail());
            modificationMedecinPanel.add(emailField);

            int result = JOptionPane.showConfirmDialog(this, modificationMedecinPanel,
                    "Modifier le médecin", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
//                    String specialite = specialiteField.getText();
//                    String adresse = adresseField.getText();
                    String telephone = telField.getText();
                    String email = emailField.getText();
                    String adrCodePostal = adresseField.getText();
                    String adrVille = adresseField.getText();
                    Adresse adresse = new Adresse(
                             null,
                            adresseField.getText(),
                            adrCodePostal,
                            adrVille
                    );
                    if (isValidName(nom) && isValidName(prenom) && isValidPhoneNumber(telephone) && isValidEmail(email)) {
                        medecinSelectionne.setNom(nom);
                        medecinSelectionne.setPrenom(prenom);
//                        medecinSelectionne.setTsTypeNom(specialite);
                        medecinSelectionne.setAdresse(adresse);
                        medecinSelectionne.setTelephone(telephone);
                        medecinSelectionne.setEmail(email);

                        medecinCombo.repaint();
                        detailsMedecinArea.setText("Détails du médecin: " + medecinSelectionne.getNom() +
                                "\nAdresse: " + medecinSelectionne.getAdresse() +
                                "\nTéléphone: " + medecinSelectionne.getTelephone() +
                                "\nEmail: " + medecinSelectionne.getEmail());
                        JOptionPane.showMessageDialog(this, "Médecin modifié !");
                    } else {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer des informations valides pour le médecin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panelMedecin.add(btnModifier, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        // Bouton pour supprimer un médecin
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setPreferredSize(buttonSize);
        btnSupprimer.setMaximumSize(buttonSize);
        btnSupprimer.addActionListener(e -> {
            TypeSpecialite medecinSelectionne = (TypeSpecialite) medecinCombo.getSelectedItem();
            if (medecinSelectionne == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médecin à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce médecin ?", "Supprimer le médecin", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                medecinCombo.removeItem(medecinSelectionne);
                detailsMedecinArea.setText("");
                JOptionPane.showMessageDialog(this, "Médecin supprimé !");
            }
        });
        panelMedecin.add(btnSupprimer, gbc);

        gbc.gridx++;
        // Bouton pour rechercher un médecin
        JButton btnRechercher = new JButton("Rechercher");
        btnRechercher.setPreferredSize(buttonSize);
        btnRechercher.setMaximumSize(buttonSize);
        btnRechercher.addActionListener(e -> {
            String searchQuery = JOptionPane.showInputDialog(this, "Entrez le nom ou prénom du médecin à rechercher:");
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                for (Medecin medecin : listeMedecins) {
                    if (medecin.getNom().toLowerCase().contains(searchQuery.toLowerCase()) || medecin.getPrenom().toLowerCase().contains(searchQuery.toLowerCase())) {
                        medecinCombo.setSelectedItem(medecin);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Aucun médecin correspondant trouvé.", "Résultat", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panelMedecin.add(btnRechercher, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        // Bouton retour
        JButton btnRetour = new JButton("Retour");
        btnRetour.setPreferredSize(buttonSize);
        btnRetour.setMaximumSize(buttonSize);
        btnRetour.addActionListener(e -> revenirAccueil());
        panelMedecin.add(btnRetour, gbc);

        gbc.gridx++;
        // Bouton quitter
        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.setPreferredSize(buttonSize);
        btnQuitter.setMaximumSize(buttonSize);
        btnQuitter.addActionListener(e -> System.exit(0));
        panelMedecin.add(btnQuitter, gbc);

        // Remplacer le contenu du panneau central
        panelCentral.add(panelMedecin);
        panelCentral.revalidate();
        panelCentral.repaint();
    }



    private void afficherDetailsClient(Client client) {
        if (client != null) {
            detailsClientArea.setText("Détails du client: " + client.getNom() + " " + client.getPrenom() +
                    "\nAdresse: " + client.getAdresse().getAdrRue() + ", " + client.getAdresse().getAdrCodePostal() + " " + client.getAdresse().getAdrVille() +
                    "\nTéléphone: " + client.getTelephone() +
                    "\nEmail: " + client.getEmail() +
                    "\nNuméro de Sécurité Social: " + client.getNumeroSecuriteSocial() +
                    "\nDate de Naissance: " + client.getDateNaissance());
        } else {
            detailsClientArea.setText("Détails du client non trouvés.");
        }
    }
    // Page pour afficher les détails d'un client
    private void afficherClientPanel() {
        panelCentral.removeAll();

        // Panneau pour gérer les clients
        JPanel panelClient = new JPanel();
        panelClient.setLayout(new BoxLayout(panelClient, BoxLayout.Y_AXIS));
        panelClient.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelClient.setBackground(new Color(230, 255, 230)); // Appliquer la même couleur que la page achat

        // Label pour le titre
        JLabel labelClient = new JLabel("Gestion des clients");
        labelClient.setFont(new Font("Arial", Font.BOLD, 18));

        // Liste déroulante pour sélectionner un client
        JLabel labelSelectClient = new JLabel("Sélectionner un client:");
//        clientCombo = new JComboBox<>(getListeClients());
        DefaultComboBoxModel<Client> comboBoxModel = new DefaultComboBoxModel<>(listeClients.toArray(new Client[0]));
        clientCombo = new JComboBox<>(comboBoxModel);
//        clientCombo.setRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                if (value instanceof Client) {
//                    Client client = (Client) value;
//                    setText(client.getNom() + " " + client.getPrenom());
//                }
//                return this;
//            }
//        });

        // Détails du client (affichage)
        detailsClientArea = new JTextArea(5, 30);
        detailsClientArea.setEditable(false);

        // Afficher les informations du client sélectionné
        clientCombo.addActionListener(e -> {
            Client clientSelectionne = (Client) clientCombo.getSelectedItem();
            if (clientSelectionne != null) {
                afficherDetailsClient(clientSelectionne);
            } else {
                detailsClientArea.setText("");
            }
        });

        // Taille uniforme pour les boutons
        Dimension buttonSize = new Dimension(150, 30);

        // Bouton pour créer un nouveau client
        JButton btnCreer = new JButton("Créer");
        btnCreer.setPreferredSize(buttonSize);
        btnCreer.setMaximumSize(buttonSize);
        btnCreer.addActionListener(e -> {
            JPanel nouveauClientPanel = new JPanel(new GridLayout(9, 2));
            nouveauClientPanel.add(new JLabel("Nom:"));
            JTextField nomField = new JTextField();
            nouveauClientPanel.add(nomField);
            nouveauClientPanel.add(new JLabel("Prénom:"));
            JTextField prenomField = new JTextField();
            nouveauClientPanel.add(prenomField);
            nouveauClientPanel.add(new JLabel("Adresse:"));
            JTextField adresseField = new JTextField();
            nouveauClientPanel.add(adresseField);
            nouveauClientPanel.add(new JLabel("Code postal:"));
            JTextField codePostalField = new JTextField();
            nouveauClientPanel.add(codePostalField);
            nouveauClientPanel.add(new JLabel("Ville:"));
            JTextField villeField = new JTextField();
            nouveauClientPanel.add(villeField);
            nouveauClientPanel.add(new JLabel("Téléphone:"));
            JTextField telField = new JTextField();
            nouveauClientPanel.add(telField);
            nouveauClientPanel.add(new JLabel("Email:"));
            JTextField emailField = new JTextField();
            nouveauClientPanel.add(emailField);
            nouveauClientPanel.add(new JLabel("Numéro de Sécurité Social:"));
            JTextField numSecuField = new JTextField();
            nouveauClientPanel.add(numSecuField);
            nouveauClientPanel.add(new JLabel("Date de Naissance (AAAA-MM-JJ):"));
            JTextField dateNaissanceField = new JTextField();
            nouveauClientPanel.add(dateNaissanceField);

            int result = JOptionPane.showConfirmDialog(this, nouveauClientPanel, "Créer un nouveau client", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String adresse = adresseField.getText();
                    String telephone = telField.getText();
                    String email = emailField.getText();
                    String numeroSecuriteSocial = numSecuField.getText();
                    LocalDate dateNaissance = LocalDate.parse(dateNaissanceField.getText());
                    String adrCodePostal = adresseField.getText();
                    String adrVille = adresseField.getText();
                    Adresse adresseClient = new Adresse(
                            null,
                            adresseField.getText(),
                            adrCodePostal,
                            adrVille
                    );

                    if (isValidName(nom) && isValidName(prenom) && isValidPhoneNumber(telephone) && isValidEmail(email)) {
                        Client nouveauClient = new Client(
                                null,  // cliId sera généré par la base de données
                                nom,
                                prenom,
                                adresseClient,
                                telephone,
                                email,
                                numeroSecuriteSocial,
                                dateNaissance,
                                null,  // Mutuelle peut être null
                                null   // Medecin peut être null
                        );

                        Client createdClient = clientDAO.create(nouveauClient);
                        if (createdClient != null) {
                            ajouterNouveauClient(createdClient);
                            clientCombo.addItem(createdClient);
                            clientCombo.setSelectedItem(createdClient);
                            afficherDetailsClient(createdClient);
                            JOptionPane.showMessageDialog(this, "Nouveau client créé !");
                        } else {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la création du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer des informations valides pour le client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Bouton pour modifier un client
        JButton btnModifier = new JButton("Modifier");
        btnModifier.setPreferredSize(buttonSize);
        btnModifier.setMaximumSize(buttonSize);
        btnModifier.addActionListener(e -> {
            Client clientSelectionne = (Client) clientCombo.getSelectedItem();
            if (clientSelectionne == null) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez sélectionner un client à modifier.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel modificationClientPanel = new JPanel(new GridLayout(6, 2));
            modificationClientPanel.add(new JLabel("Nom:"));
            JTextField nomField = new JTextField(clientSelectionne.getNom());
            modificationClientPanel.add(nomField);
            modificationClientPanel.add(new JLabel("Prénom:"));
            JTextField prenomField = new JTextField(clientSelectionne.getPrenom());
            modificationClientPanel.add(prenomField);
            modificationClientPanel.add(new JLabel("Adresse:"));
            JTextField adresseField = new JTextField(clientSelectionne.getAdresse().getAdrRue());
            modificationClientPanel.add(adresseField);
            modificationClientPanel.add(new JLabel("Code postal:"));
            JTextField codePostalField = new JTextField(clientSelectionne.getAdresse().getAdrCodePostal());
            modificationClientPanel.add(codePostalField);
            modificationClientPanel.add(new JLabel("ville:"));
            JTextField villeField = new JTextField(clientSelectionne.getAdresse().getAdrVille());
            modificationClientPanel.add(villeField);
            modificationClientPanel.add(new JLabel("Téléphone:"));
            JTextField telField = new JTextField(clientSelectionne.getTelephone());
            modificationClientPanel.add(telField);
            modificationClientPanel.add(new JLabel("Email:"));
            JTextField emailField = new JTextField(clientSelectionne.getEmail());
            modificationClientPanel.add(emailField);

            int result = JOptionPane.showConfirmDialog(this, modificationClientPanel,
                    "Modifier le client", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nomModifie = nomField.getText();
                    String prenomModifie = prenomField.getText();
                    String adresseModifiee = adresseField.getText();
                    String telephoneModifie = telField.getText();
                    String emailModifie = emailField.getText();

                    if (isValidName(nomModifie) && isValidName(prenomModifie) && isValidPhoneNumber(telephoneModifie) && isValidEmail(emailModifie)) {
                        clientSelectionne.setNom(nomModifie);
                        clientSelectionne.setPrenom(prenomModifie);
                        clientSelectionne.getAdresse().setAdrRue(adresseModifiee);
                        clientSelectionne.setTelephone(telephoneModifie);
                        clientSelectionne.setEmail(emailModifie);

                        if (clientDAO.update(clientSelectionne)) {
                            clientCombo.repaint();
                            afficherDetailsClient(clientSelectionne);
                            JOptionPane.showMessageDialog(this, "Client modifié !");
                        } else {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Veuillez entrer des informations valides pour le client.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Bouton pour supprimer un client
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setPreferredSize(buttonSize);
        btnSupprimer.setMaximumSize(buttonSize);
        btnSupprimer.addActionListener(e -> {
            Client clientSelectionne = (Client) clientCombo.getSelectedItem();
            if (clientSelectionne == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce client ?", "Supprimer le client", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                if (clientDAO.delete(clientSelectionne.getCliId())) {
                    supprimerClient(clientSelectionne);
                    clientCombo.removeItem(clientSelectionne);
                    detailsClientArea.setText("");
                    JOptionPane.showMessageDialog(this, "Client supprimé !");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Bouton pour rechercher un client
        JButton btnRechercher = new JButton("Rechercher");
        btnRechercher.setPreferredSize(buttonSize);
        btnRechercher.setMaximumSize(buttonSize);

        btnRechercher.addActionListener(e -> {
            String searchQuery = JOptionPane.showInputDialog(this, "Entrez le nom ou prénom du client à rechercher:");
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                for (Client client : listeClients) {
                    if (client.getNom().toLowerCase().contains(searchQuery.toLowerCase()) || client.getPrenom().toLowerCase().contains(searchQuery.toLowerCase())) {
                        clientCombo.setSelectedItem(client);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Aucun client correspondant trouvé.", "Résultat", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Panel pour les boutons
        JPanel boutonsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        boutonsPanel.add(btnModifier);
        boutonsPanel.add(btnSupprimer);
        boutonsPanel.add(btnCreer);
        boutonsPanel.add(btnRechercher);

        // Boutons retour et quitter
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> revenirAccueil());

        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(e -> System.exit(0));

        // Ajout des composants au panneau de gestion des clients
        panelClient.add(labelClient);
        panelClient.add(Box.createRigidArea(new Dimension(0, 10)));
        panelClient.add(labelSelectClient);
        panelClient.add(clientCombo);
        panelClient.add(Box.createRigidArea(new Dimension(0, 10)));
        panelClient.add(new JScrollPane(detailsClientArea));
        panelClient.add(Box.createRigidArea(new Dimension(0, 10)));
        panelClient.add(boutonsPanel);
        panelClient.add(Box.createRigidArea(new Dimension(0, 10)));
        panelClient.add(btnRetour);
        panelClient.add(btnQuitter);

        // Remplacer le contenu du panneau central
        panelCentral.add(panelClient);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    // Liste simulée des médecins
//    private String[] getListeMedecins() {
//        return new String[]{
//                "Dr Jean Dupont (Généraliste)",
//                "Dr Claire Martin (Cardiologue)",
//                "Dr Pierre Bernard (Dermatologue)"
//        };
//    }

    // Méthode utilitaire pour ajouter l'option "Aucun" à une liste
//    private String[] addOptionAucun(List<Client> liste) {
//        String[] listeAvecAucun = new String[liste.length + 1];
//        listeAvecAucun[0] = "Aucun";  // Ajouter l'option "Aucun" en première position
//        System.arraycopy(liste, 0, listeAvecAucun, 1, liste.length);
//        return listeAvecAucun;
//    }

    // Méthode principale
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardView::new);
    }
}

