package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adherer;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;
import fr.afpa.pompey.cda22045.utilities.ValidationUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdhererDAO extends DAO<Adherer> {

    @Override
    public Adherer create(Adherer obj) {
        if (!ValidationUtils.isFieldValid(obj.getNiveauCouverture(), "Niveau de couverture")) {
            return null;
        }

        String sql = "INSERT INTO Adherer (cli_id, mut_id, date_adhesion, niveau_couverture) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, obj.getClientId());
            stmt.setInt(2, obj.getMutuelleId());
            stmt.setDate(3, Date.valueOf(obj.getDateAdhesion()));
            stmt.setString(4, obj.getNiveauCouverture());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setAdhId(rs.getInt(1));
                }
            }
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM Adherer WHERE adh_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Adherer obj) {
        String sql = "UPDATE Adherer SET niveau_couverture = ? WHERE adh_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNiveauCouverture());
            stmt.setInt(2, obj.getAdhId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Adherer getById(int id) {
        String sql = "SELECT adh_id, cli_id, mut_id, date_adhesion, niveau_couverture FROM Adherer WHERE adh_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Adherer(
                            rs.getInt("adh_id"),
                            rs.getInt("cli_id"),
                            rs.getInt("mut_id"),
                            rs.getDate("date_adhesion").toLocalDate(),
                            rs.getString("niveau_couverture")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Adherer> getAll() {
        String sql = "SELECT adh_id, cli_id, mut_id, date_adhesion, niveau_couverture FROM Adherer";
        List<Adherer> adhererList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                adhererList.add(new Adherer(
                        rs.getInt("adh_id"),
                        rs.getInt("cli_id"),
                        rs.getInt("mut_id"),
                        rs.getDate("date_adhesion").toLocalDate(),
                        rs.getString("niveau_couverture")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adhererList;
    }
}
