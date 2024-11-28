package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Specialiste;
import fr.afpa.pompey.cda22045.enums.enumTypeSpecialite;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialisteDAO extends DAO<Specialiste> {

    @Override
    public Specialiste create(Specialiste obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Le spécialiste ne peut pas être null.");
        }

        String sqlMedecin = "INSERT INTO medecin (uti_id, numero_agrement) VALUES (?, ?)";
        String sqlSpecialiste = "INSERT INTO specialiste (med_id, type_specialiste_id) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getInstanceDB()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statementMedecin = connection.prepareStatement(sqlMedecin, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementSpecialiste = connection.prepareStatement(sqlSpecialiste, PreparedStatement.RETURN_GENERATED_KEYS)) {

                // Insertion dans Medecin
                statementMedecin.setInt(1, obj.getUserId());
                statementMedecin.setString(2, obj.getMedNumAgreement());
                statementMedecin.executeUpdate();

                try (ResultSet generatedKeys = statementMedecin.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setUserId(generatedKeys.getInt(1)); // L'ID utilisateur est aussi l'ID médecin
                    }
                }

                // Insertion dans Specialiste
                statementSpecialiste.setInt(1, obj.getUserId());
                statementSpecialiste.setInt(2, obj.getTypeSpecialite().getId());
                statementSpecialiste.executeUpdate();

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
        String sql = "DELETE FROM specialiste WHERE med_id = ?";
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
    public boolean update(Specialiste obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Le spécialiste ne peut pas être null.");
        }

        String sql = "UPDATE specialiste SET type_specialiste_id = ? WHERE med_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, obj.getTypeSpecialite().getId());
            statement.setInt(2, obj.getUserId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Specialiste getById(int id) {
        String sql = "SELECT m.*, s.type_specialiste_id, ts.type_nom " +
                "FROM specialiste s " +
                "JOIN medecin m ON s.med_id = m.uti_id " +
                "JOIN type_specialiste ts ON s.type_specialiste_id = ts.type_id " +
                "WHERE m.uti_id = ?";

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    enumTypeSpecialite typeSpecialiste = enumTypeSpecialite.valueOf(resultSet.getString("type_nom"));

                    return new Specialiste(
                            resultSet.getInt("uti_id"),
                            resultSet.getString("uti_nom"),
                            resultSet.getString("uti_prenom"),
                            null, // Adresse à ajouter si nécessaire
                            resultSet.getString("uti_tel"),
                            resultSet.getString("uti_email"),
                            resultSet.getString("numero_agrement"),
                            typeSpecialiste
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Specialiste> getAll() {
        String sql = "SELECT m.*, s.type_specialiste_id, ts.type_nom " +
                "FROM specialiste s " +
                "JOIN medecin m ON s.med_id = m.uti_id " +
                "JOIN type_specialiste ts ON s.type_specialiste_id = ts.type_id";

        List<Specialiste> specialistes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                enumTypeSpecialite typeSpecialiste = enumTypeSpecialite.valueOf(resultSet.getString("type_nom"));

                specialistes.add(new Specialiste(
                        resultSet.getInt("uti_id"),
                        resultSet.getString("uti_nom"),
                        resultSet.getString("uti_prenom"),
                        null, // Adresse peut être ajoutée si nécessaire
                        resultSet.getString("uti_tel"),
                        resultSet.getString("uti_email"),
                        resultSet.getString("numero_agrement"),
                        typeSpecialiste
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialistes;
    }
}
