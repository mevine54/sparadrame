package fr.afpa.pompey.cda22045;

import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getInstanceDB(); // Correct usage
            if (connection != null) {
                System.out.println("Database connection successful: " + connection.isValid(2));
            } else {
                System.out.println("Failed to establish a database connection.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connection after the test
            DatabaseConnection.closeInstanceDB();
        }
    }
}
