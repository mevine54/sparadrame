package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.TypeMedicament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.DatabaseConnection.getConnection;

public class TypeMedicamentDAO extends DAO<TypeMedicament> {

    @Override
    public TypeMedicament create(TypeMedicament obj) {
        String sql = "INSERT INTO typeMedicament (tm_type_nom) VALUES (?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, obj.getTmTypeNom());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setTmTypeId(generatedKeys.getInt(1));
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
        String sql = "DELETE FROM typemedicament WHERE tm_type_id = ?";
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
    public boolean update(TypeMedicament obj) {
        String sql = "UPDATE typemedicament SET tm_type_nom = ? WHERE tm_type_id = ?";
        boolean updated = false;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, obj.getTmTypeNom());
            statement.setInt(2, obj.getTmTypeId());

            int affectedRows = statement.executeUpdate();
            updated = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updated;
    }


    @Override
    public TypeMedicament getById(int id) {
        String sql = "SELECT * FROM typemedicament WHERE type_id = ?";
        TypeMedicament typeMedicament = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String tmTypeNom = resultSet.getString("tm_type_nom");
                    typeMedicament = new TypeMedicament(id, tmTypeNom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typeMedicament;
    }


    @Override
    public List<TypeMedicament> getAll() {
        String sql = "SELECT " +
                "m.medi_id AS medicament_id, " +
                "m.medi_nom AS medicament_nom, " +
                "m.medi_prix AS medicament_prix, " +
                "m.medi_date_mise_en_service AS medicament_date_mise_en_service, " +
                "m.medi_quantite AS medicament_quantite, " +
                "t.tm_type_id AS type_medicament_id " +
                "FROM " +
                "MEDICAMENT m " +
                "JOIN TYPEMEDICAMENT t ON m.type_id = t.tm.type_id ";

        List<TypeMedicament> typeMedicaments = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("tm_type_id");
                String typeNom = resultSet.getString("tm_type_nom");
                typeMedicaments.add(new TypeMedicament(id, typeNom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typeMedicaments;
    }

}

