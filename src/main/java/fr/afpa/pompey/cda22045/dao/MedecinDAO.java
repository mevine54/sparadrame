package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.DatabaseConnection.getConnection;


public class MedecinDAO extends DAO<Medecin> {

    @Override
    public Medecin create(Medecin obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Le médecin ne peut pas être null.");
        }

        String sqlUtilisateur = "INSERT INTO utilisateur (uti_nom, uti_prenom, uti_tel, uti_email) VALUES (?, ?, ?, ?)";
        String sqlMedecin = "INSERT INTO medecin (uti_id, numero_agrement) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getInstanceDB()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statementUtilisateur = connection.prepareStatement(sqlUtilisateur, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementMedecin = connection.prepareStatement(sqlMedecin, PreparedStatement.RETURN_GENERATED_KEYS)) {

                // Insertion dans utilisateur
                statementUtilisateur.setString(1, obj.getNom());
                statementUtilisateur.setString(2, obj.getPrenom());
                statementUtilisateur.setString(3, obj.getTelephone());
                statementUtilisateur.setString(4, obj.getEmail());
                statementUtilisateur.executeUpdate();

                try (ResultSet generatedKeys = statementUtilisateur.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setUserId(generatedKeys.getInt(1));
                    }
                }

                // Insertion dans médecin
                statementMedecin.setInt(1, obj.getUserId());
                statementMedecin.setString(2, obj.getMedNumAgreement());
                statementMedecin.executeUpdate();

                connection.commit();
                return obj;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM medecin WHERE uti_id = ?";
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
    public boolean update(Medecin obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Le médecin ne peut pas être null.");
        }

        String sql = "UPDATE medecin SET numero_agrement = ? WHERE uti_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getMedNumAgreement());
            statement.setInt(2, obj.getUserId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Medecin getById(int id) {
        String sql = "SELECT * FROM medecin m JOIN utilisateur u ON m.uti_id = u.uti_id WHERE m.uti_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Medecin(
                            resultSet.getInt("uti_id"),
                            resultSet.getString("uti_nom"),
                            resultSet.getString("uti_prenom"),
                            null, // Adresse à ajouter si nécessaire
                            resultSet.getString("uti_tel"),
                            resultSet.getString("uti_email"),
                            resultSet.getString("med_num_agreement")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medecin> getAll() {
        String sql = "SELECT * FROM medecin m JOIN utilisateur u ON m.uti_id = u.uti_id";
        List<Medecin> medecins = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                medecins.add(new Medecin(
                        resultSet.getInt("uti_id"),
                        resultSet.getString("uti_nom"),
                        resultSet.getString("uti_prenom"),
                        null, // Adresse peut être ajoutée si nécessaire
                        resultSet.getString("uti_tel"),
                        resultSet.getString("uti_email"),
                        resultSet.getString("med_num_agreement")


                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecins;
    }
}
