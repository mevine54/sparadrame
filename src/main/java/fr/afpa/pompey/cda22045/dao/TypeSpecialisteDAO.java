package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.TypeSpecialiste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.DatabaseConnection.getConnection;

public class TypeSpecialisteDAO extends DAO<TypeSpecialiste> {

    @Override
    public TypeSpecialiste create(TypeSpecialiste obj) {
        String sql = "INSERT INTO typespecialiste (type_nom) VALUES (?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, obj.getTypeNom());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setTypeId(generatedKeys.getInt(1)); // Récupération de l'ID généré
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM typespecialiste WHERE type_id = ?";
        boolean deleted = false;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, (int) id);

            int affectedRows = statement.executeUpdate();
            deleted = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleted;
    }


    @Override
    public boolean update(TypeSpecialiste obj) {
        String sql = "UPDATE typespecialiste SET type_nom = ? WHERE type_id = ?";
        boolean updated = false;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getTypeNom());
            statement.setInt(2, obj.getTypeId());

            int affectedRows = statement.executeUpdate();
            updated = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updated;
    }


    @Override
    public TypeSpecialiste getById(int id) {
        String sql = "SELECT * FROM typespecialiste WHERE type_id = ?";
        TypeSpecialiste typeSpecialiste = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String typeNom = resultSet.getString("type_nom");
                    typeSpecialiste = new TypeSpecialiste(id, typeNom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typeSpecialiste;
    }

    @Override
    public List<TypeSpecialiste> getAll() {
        String sql = "SELECT " +
                "s.spe_id AS specialiste_id, " +
                "s.spe_nom AS specialiste_nom, " +
                "s.type_id AS specialite_type_id, " +
                "med_id AS medecin_id, " +
                "adr_id AS adresse_id " +
                "FROM " +
                "SPECIALISTE s " +
                "JOIN TYPESPECIALISTE ts ON s.type_id = ts.type_id ";

        List<TypeSpecialiste> typeSpecialistes = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("type_id");
                String typeNom = resultSet.getString("type_nom");
                typeSpecialistes.add(new TypeSpecialiste(id, typeNom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typeSpecialistes;
    }

}
