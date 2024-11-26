package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Effectuer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EffectuerDAO {
    private Connection connection;

    public EffectuerDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Effectuer effectuer) throws SQLException {
        String query = "INSERT INTO Effectuer (achat_id, utilisateur_id, date_effectuee) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, effectuer.getAchatId());
            stmt.setInt(2, effectuer.getUtilisateurId());
            stmt.setDate(3, Date.valueOf(effectuer.getDateEffectuee()));
            stmt.executeUpdate();
        }
    }

    public Effectuer read(int id) throws SQLException {
        String query = "SELECT * FROM Effectuer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Effectuer(
                            rs.getInt("id"),
                            rs.getInt("achat_id"),
                            rs.getInt("utilisateur_id"),
                            rs.getDate("date_effectuee").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    public List<Effectuer> readAll() throws SQLException {
        List<Effectuer> list = new ArrayList<>();
        String query = "SELECT * FROM Effectuer";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Effectuer(
                        rs.getInt("id"),
                        rs.getInt("achat_id"),
                        rs.getInt("utilisateur_id"),
                        rs.getDate("date_effectuee").toLocalDate()
                ));
            }
        }
        return list;
    }

    public void update(Effectuer effectuer) throws SQLException {
        String query = "UPDATE Effectuer SET achat_id = ?, utilisateur_id = ?, date_effectuee = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, effectuer.getAchatId());
            stmt.setInt(2, effectuer.getUtilisateurId());
            stmt.setDate(3, Date.valueOf(effectuer.getDateEffectuee()));
            stmt.setInt(4, effectuer.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Effectuer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
