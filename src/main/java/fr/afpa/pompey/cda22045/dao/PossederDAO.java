package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Posseder;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PossederDAO extends DAO<Posseder> {

    @Override
    public Posseder create(Posseder obj) {
        if (obj == null) {
            throw new IllegalArgumentException("La relation de possession ne peut pas être null.");
        }

        String sql = "INSERT INTO posseder (uti_id, adr_id, type_possession) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, obj.getUtilisateurId());
            statement.setInt(2, obj.getAdresseId());
            statement.setString(3, obj.getTypePossession());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de la relation de possession.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setPossId(generatedKeys.getInt(1));
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
        String sql = "DELETE FROM posseder WHERE poss_id = ?";
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
    public boolean update(Posseder obj) {
        if (obj == null) {
            throw new IllegalArgumentException("La relation de possession ne peut pas être null.");
        }

        String sql = "UPDATE posseder SET uti_id = ?, adr_id = ?, type_possession = ? WHERE poss_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, obj.getUtilisateurId());
            statement.setInt(2, obj.getAdresseId());
            statement.setString(3, obj.getTypePossession());
            statement.setInt(4, obj.getPossId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Posseder getById(int id) {
        String sql = "SELECT * FROM posseder WHERE poss_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Posseder(
                            resultSet.getInt("poss_id"),
                            resultSet.getInt("uti_id"),
                            resultSet.getInt("adr_id"),
                            resultSet.getString("type_possession")
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
        String sql = "SELECT * FROM posseder";
        List<Posseder> possessions = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                possessions.add(new Posseder(
                        resultSet.getInt("poss_id"),
                        resultSet.getInt("uti_id"),
                        resultSet.getInt("adr_id"),
                        resultSet.getString("type_possession")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return possessions;
    }
}
