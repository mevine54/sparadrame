package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Mutuelle;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MutuelleDAO extends DAO<Mutuelle> {

    @Override
    public Mutuelle create(Mutuelle obj) {
        if (obj == null) {
            throw new IllegalArgumentException("La mutuelle ne peut pas être null.");
        }

        String sql = "INSERT INTO mutuelle (mut_nom, mut_tel, mut_email, mut_departement, mut_taux_prise_en_charge) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getTelephone());
            statement.setString(3, obj.getEmail());
            statement.setString(4, obj.getDepartement());
            statement.setDouble(5, obj.getTauxPriseEnCharge());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de la mutuelle, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setMutId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Échec de la création de la mutuelle, aucun ID généré.");
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
        String sql = "DELETE FROM mutuelle WHERE mut_id = ?";
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
    public boolean update(Mutuelle obj) {
        if (obj == null) {
            throw new IllegalArgumentException("La mutuelle ne peut pas être null.");
        }

        String sql = "UPDATE mutuelle SET mut_nom = ?, mut_tel = ?, mut_email = ?, mut_departement = ?, " +
                "mut_taux_prise_en_charge = ? WHERE mut_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getTelephone());
            statement.setString(3, obj.getEmail());
            statement.setString(4, obj.getDepartement());
            statement.setDouble(5, obj.getTauxPriseEnCharge());
            statement.setInt(6, obj.getMutId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Mutuelle getById(int id) {
        String sql = "SELECT * FROM mutuelle WHERE mut_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Mutuelle(
                            resultSet.getInt("mut_id"),
                            resultSet.getString("mut_nom"),
                            null, // Adresse peut être récupérée si disponible
                            resultSet.getString("mut_tel"),
                            resultSet.getString("mut_email"),
                            resultSet.getString("mut_departement"),
                            resultSet.getDouble("mut_taux_prise_en_charge")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Mutuelle> getAll() {
        String sql = "SELECT * FROM mutuelle";
        List<Mutuelle> mutuelles = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                mutuelles.add(new Mutuelle(
                        resultSet.getInt("mut_id"),
                        resultSet.getString("mut_nom"),
                        null, // Adresse peut être ajoutée si nécessaire
                        resultSet.getString("mut_tel"),
                        resultSet.getString("mut_email"),
                        resultSet.getString("mut_departement"),
                        resultSet.getDouble("mut_taux_prise_en_charge")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mutuelles;
    }
}