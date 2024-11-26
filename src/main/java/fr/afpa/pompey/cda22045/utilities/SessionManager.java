package fr.afpa.pompey.cda22045.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionManager {

    // Méthode pour connecter un utilisateur
    public static boolean loginUser(int userId) {
        String query = "INSERT INTO Session (uti_id) VALUES (?) ON DUPLICATE KEY UPDATE session_start = CURRENT_TIMESTAMP";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour déconnecter un utilisateur
    public static boolean logoutUser(int userId) {
        String query = "DELETE FROM Session WHERE uti_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour récupérer l'utilisateur actuellement connecté
    public static Integer getCurrentUserId() {
        String query = "SELECT uti_id FROM Session ORDER BY session_start DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("uti_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Aucun utilisateur connecté
    }
}
