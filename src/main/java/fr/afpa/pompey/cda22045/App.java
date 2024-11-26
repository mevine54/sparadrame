package fr.afpa.pompey.cda22045;

import fr.afpa.pompey.cda22045.dao.ClientDAO;
import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Utilisateur;
import fr.afpa.pompey.cda22045.views.DashboardView;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import javax.swing.*;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello Sparadrap Mévine !");

        // Créer une instance de la classe App
        App app = new App();

//         Appeler la méthode propertiesConnection
//        Connection connection = app.propertiesConnection();

//        app.selectFromUtilisateur(connection);

        //Connection conn1 = Singleton.getInstanceDB();
        //Connection conn2 = Singleton.getInstanceDB();
        //System.out.println(conn1);
        //System.out.println(conn2);

        ClientDAO  clientDAO = new ClientDAO();
        clientDAO.getAll();
        clientDAO.getById(1);
//
//        try {
//            selectFromUtilisateur(conn);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Singleton.closeInstanceDB();

        // *** Affichage de l'interface graphique (Swing) ***
        SwingUtilities.invokeLater(() -> {
            new DashboardView(); // Affiche l'interface graphique Swing
        });

    }

//    private Connection propertiesConnection() {
//        // chemin vers le fichier de propriétés
//        final String PATHCONF = "conf.properties";
//
//        // attribut properties
//        Properties prop = new Properties();
//
//        // chargement du fichier de propriétés
//        try (InputStream is = getClass().getClassLoader().getResourceAsStream(PATHCONF)) {
//
//            prop.load(is);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // traitement du fichier de propriétés et de la connexion à la BDD
//        try {
//            // chargement du driver
//            Class.forName(prop.getProperty("jdbc.driver.class"));
//
//            // récupération des paramètres pour la connexion
//            String url = prop.getProperty("jdbc.url");
//            String user = prop.getProperty("jdbc.user");
//            String password = prop.getProperty("jdbc.password");
//
//            Connection connection = DriverManager.getConnection(url, user, password);
//
//            // petit message indiquant que tout s'est bien passé
//            System.out.println("Connected to database");
//            return connection;
//            // fermeture de la connexion
////            connection.close();
//
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


//    private static void selectFromUtilisateur(Connection con) {
//        String selectSQL =  "SELECT a.ADR_RUE, a.ADR_CODE_POSTAL, a.ADR_VILLE, u.UTI_NOM, u.UTI_PRENOM, u.UTI_TEL, u.UTI_EMAIL " +
//                "FROM adresse a " +
//                "JOIN utilisateur u ON a.ADR_ID = u.ADR_ID";
//
//        try (Statement stmt = con.createStatement();
//             ResultSet resultSet = stmt.executeQuery(selectSQL)) {
//
//            while (resultSet.next()) {
//                System.out.println("Nom: " + resultSet.getString("UTI_NOM"));
//                System.out.println("Prénom: " + resultSet.getString("UTI_PRENOM"));
//                System.out.println("Téléphone: " + resultSet.getString("UTI_TEL"));
//                System.out.println("Email: " + resultSet.getString("UTI_EMAIL"));
//                System.out.println("Adresse: " + resultSet.getString("ADR_RUE") + ", " +
//                        resultSet.getString("ADR_CODE_POSTAL") + " " +
//                        resultSet.getString("ADR_VILLE"));
//                System.out.println("----------------");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }





}




