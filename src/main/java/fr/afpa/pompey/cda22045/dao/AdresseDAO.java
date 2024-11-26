package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresseDAO extends DAO<Adresse> {

    @Override
    public Adresse create(Adresse obj) {
        if (obj == null) {
            throw new IllegalArgumentException("L'adresse ne peut pas être null.");
        }

        String sql = "INSERT INTO adresse (adr_rue, adr_code_postal, adr_ville) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, obj.getRue());
            statement.setString(2, obj.getCodePostal());
            statement.setString(3, obj.getVille());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de l'adresse, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setAdrId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Échec de la création de l'adresse, aucun ID généré.");
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
        String sql = "DELETE FROM adresse WHERE adr_id = ?";
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
    public boolean update(Adresse obj) {
        if (obj == null) {
            throw new IllegalArgumentException("L'adresse ne peut pas être null.");
        }

        String sql = "UPDATE adresse SET adr_rue = ?, adr_code_postal = ?, adr_ville = ? WHERE adr_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getRue());
            statement.setString(2, obj.getCodePostal());
            statement.setString(3, obj.getVille());
            statement.setInt(4, obj.getAdrId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Adresse getById(int id) {
        String sql = "SELECT * FROM adresse WHERE adr_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Adresse(
                            resultSet.getInt("adr_id"),
                            resultSet.getString("adr_rue"),
                            resultSet.getString("adr_code_postal"),
                            resultSet.getString("adr_ville")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Adresse> getAll() {
        String sql = "SELECT * FROM adresse";
        List<Adresse> adresses = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                adresses.add(new Adresse(
                        resultSet.getInt("adr_id"),
                        resultSet.getString("adr_rue"),
                        resultSet.getString("adr_code_postal"),
                        resultSet.getString("adr_ville")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adresses;
    }
}
