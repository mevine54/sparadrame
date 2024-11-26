package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Posseder;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;
import fr.afpa.pompey.cda22045.utilities.ValidationUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PossederDAO extends DAO<Posseder> {

    @Override
    public Posseder create(Posseder obj) {
        if (!ValidationUtils.isFieldValid(obj.getTypePossession(), "Type de possession")) {
            return null;
        }

        String sql = "INSERT INTO Posseder (uti_id, adr_id, type_possession) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, obj.getUtilisateurId());
            stmt.setInt(2, obj.getAdresseId());
            stmt.setString(3, obj.getTypePossession());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setPossId(rs.getInt(1));
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
        String sql = "DELETE FROM Posseder WHERE poss_id = ?";
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
    public boolean update(Posseder obj) {
        String sql = "UPDATE Posseder SET type_possession = ? WHERE poss_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getTypePossession());
            stmt.setInt(2, obj.getPossId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Posseder getById(int id) {
        String sql = "SELECT poss_id, uti_id, adr_id, type_possession FROM Posseder WHERE poss_id = ?";
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Posseder(
                            rs.getInt("poss_id"),
                            rs.getInt("uti_id"),
                            rs.getInt("adr_id"),
                            rs.getString("type_possession")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Posseder> getAll() {
        String sql = "SELECT poss_id, uti_id, adr_id, type_possession FROM Posseder";
        List<Posseder> possederList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstanceDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                possederList.add(new Posseder(
                        rs.getInt("poss_id"),
                        rs.getInt("uti_id"),
                        rs.getInt("adr_id"),
                        rs.getString("type_possession")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return possederList;
    }
}
