package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Achat;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AchatDAO extends DAO<Achat> {

    @Override
    public Achat create(Achat obj) {
        String query = "INSERT INTO achat (ach_type, ach_date, uti_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstanceDB(); // Reuse the shared connection
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, obj.getType());
            stmt.setDate(2, new java.sql.Date(obj.getDateAchat().getTime()));
            stmt.setInt(3, obj.getUtilisateurId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                obj.setId(rs.getInt(1));
            }
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        String query = "DELETE FROM achat WHERE ach_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB(); // Reuse the shared connection
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Achat obj) {
        String query = "UPDATE achat SET ach_type = ?, ach_date = ?, uti_id = ? WHERE ach_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB(); // Reuse the shared connection
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, obj.getType());
            stmt.setDate(2, new java.sql.Date(obj.getDateAchat().getTime()));
            stmt.setInt(3, obj.getUtilisateurId());
            stmt.setInt(4, obj.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Achat getById(int id) {
        String query = "SELECT ach_id, ach_type, ach_date, uti_id FROM achat WHERE ach_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB(); // Reuse the shared connection
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Achat(
                            rs.getInt("ach_id"),
                            rs.getString("ach_type"),
                            rs.getDate("ach_date"),
                            rs.getInt("uti_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Achat> getAll() {
        String query = "SELECT ach_id, ach_type, ach_date, uti_id FROM achat";
        List<Achat> achats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstanceDB(); // Reuse the shared connection
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                achats.add(new Achat(
                        rs.getInt("ach_id"),
                        rs.getString("ach_type"),
                        rs.getDate("ach_date"),
                        rs.getInt("uti_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return achats;
    }
}
