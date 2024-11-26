package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Delivrer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DelivrerDAO {
    private Connection connection;

    public DelivrerDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public void create(Delivrer delivrer) throws SQLException {
        if (delivrer == null) {
            throw new IllegalArgumentException("L'objet Delivrer ne peut pas être null.");
        }

        String query = "INSERT INTO Delivrer (ordonnance_id, medicament_id, quantite) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, delivrer.getOrdonnanceId());
            stmt.setInt(2, delivrer.getMedicamentId());
            stmt.setInt(3, delivrer.getQuantite());
            stmt.executeUpdate();
        }
    }

    // Read
    public Delivrer read(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID doit être strictement positif.");
        }

        String query = "SELECT * FROM Delivrer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Delivrer(
                            rs.getInt("id"),
                            rs.getInt("ordonnance_id"),
                            rs.getInt("medicament_id"),
                            rs.getInt("quantite")
                    );
                }
            }
        }
        return null;
    }

    // Read All
    public List<Delivrer> readAll() throws SQLException {
        List<Delivrer> list = new ArrayList<>();
        String query = "SELECT * FROM Delivrer";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Delivrer(
                        rs.getInt("id"),
                        rs.getInt("ordonnance_id"),
                        rs.getInt("medicament_id"),
                        rs.getInt("quantite")
                ));
            }
        }
        return list;
    }

    // Update
    public void update(Delivrer delivrer) throws SQLException {
        if (delivrer == null) {
            throw new IllegalArgumentException("L'objet Delivrer ne peut pas être null.");
        }
        if (delivrer.getId() == null || delivrer.getId() <= 0) {
            throw new IllegalArgumentException("L'ID de Delivrer doit être défini et strictement positif.");
        }

        String query = "UPDATE Delivrer SET ordonnance_id = ?, medicament_id = ?, quantite = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, delivrer.getOrdonnanceId());
            stmt.setInt(2, delivrer.getMedicamentId());
            stmt.setInt(3, delivrer.getQuantite());
            stmt.setInt(4, delivrer.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void delete(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID doit être strictement positif.");
        }

        String query = "DELETE FROM Delivrer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
