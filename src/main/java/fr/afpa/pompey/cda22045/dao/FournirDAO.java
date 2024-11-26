package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Fournir;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FournirDAO {
    private Connection connection;

    public FournirDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Fournir fournir) throws SQLException {
        String query = "INSERT INTO Fournir (specialiste_id, type_specialiste_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, fournir.getSpecialisteId());
            stmt.setInt(2, fournir.getTypeSpecialisteId());
            stmt.executeUpdate();
        }
    }

    public Fournir read(int id) throws SQLException {
        String query = "SELECT * FROM Fournir WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Fournir(
                            rs.getInt("id"),
                            rs.getInt("specialiste_id"),
                            rs.getInt("type_specialiste_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Fournir> readAll() throws SQLException {
        List<Fournir> list = new ArrayList<>();
        String query = "SELECT * FROM Fournir";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Fournir(
                        rs.getInt("id"),
                        rs.getInt("specialiste_id"),
                        rs.getInt("type_specialiste_id")
                ));
            }
        }
        return list;
    }

    public void update(Fournir fournir) throws SQLException {
        String query = "UPDATE Fournir SET specialiste_id = ?, type_specialiste_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, fournir.getSpecialisteId());
            stmt.setInt(2, fournir.getTypeSpecialisteId());
            stmt.setInt(3, fournir.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Fournir WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
