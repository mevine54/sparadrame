package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Achat;
import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AchatDAO extends DAO<Achat> {

    private ClientDAO clientDAO;

    @Override
    public Achat create(Achat obj) {
        if (obj == null) {
            throw new IllegalArgumentException("L'achat ne peut pas être null.");
        }

        String sql = "INSERT INTO achat (ach_type, ach_date, uti_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, obj.getType());
            statement.setDate(2, Date.valueOf(obj.getDateAchat()));
            statement.setInt(3, obj.getClient().getUserId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de l'achat : aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setAchId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Échec de la création de l'achat : aucun ID généré.");
                }
            }
            return obj;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM achat WHERE ach_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Achat obj) {
        if (obj == null) {
            throw new IllegalArgumentException("L'achat ne peut pas être null.");
        }

        String sql = "UPDATE achat SET ach_type = ?, ach_date = ?, uti_id = ? WHERE ach_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getType());
            statement.setDate(2, java.sql.Date.valueOf(obj.getDateAchat()));
            statement.setInt(3, obj.getClient().getUserId());
            statement.setInt(4, obj.getAchId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0; // Retourne `true` si une ligne a été mise à jour

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Achat getById(int id) {
        String sql = "SELECT ach_id, ach_type, ach_date, uti_id FROM achat WHERE ach_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = clientDAO.getById(resultSet.getInt("uti_id"));
                    return new Achat(
                            resultSet.getInt("ach_id"),
                            resultSet.getString("ach_type"),
                            resultSet.getDate("ach_date").toLocalDate(),
                            client
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
        String sql = "SELECT ach_id, ach_type, ach_date, uti_id FROM achat";
        List<Achat> achats = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Client client = clientDAO.getById(resultSet.getInt("uti_id"));

                achats.add(new Achat(
                        resultSet.getInt("ach_id"),
                        resultSet.getString("ach_type"),
                        resultSet.getDate("ach_date").toLocalDate(),
                        client
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return achats;
    }
}
