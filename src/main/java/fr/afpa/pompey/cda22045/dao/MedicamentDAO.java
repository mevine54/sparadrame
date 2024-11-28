package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Medicament;
import fr.afpa.pompey.cda22045.models.TypeMedicament;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.DatabaseConnection.getConnection;

public class MedicamentDAO extends DAO<Medicament> {



    @Override
    public Medicament create(Medicament obj) {
        String sql = "INSERT INTO medicament (medi_nom, medi_prix, medi_date_mise_en_service, medi_quantite, TypeMedicamentEnum) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);


        statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setString(1, obj.getMediNom());
        statement.setDouble(2, obj.getMediPrix());
        statement.setDate(3, java.sql.Date.valueOf(obj.getMediDateMiseEnService()));
        statement.setInt(4, obj.getMediQuantite());
        statement.setString(5, obj.getTypeMedicament().getTmTypeNom());

        int affectedRows = statement.executeUpdate();
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setMediId(generatedKeys.getInt(1));
                }
            }
        }
        connection.commit();
    } catch (SQLException e) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
        e.printStackTrace();
    } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return obj;
        }
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean update(Medicament obj) {
        return false;
    }

    @Override
    public Medicament getById(int id) {
        return null;
    }

    @Override
    public List<Medicament> getAll() {
        String sql = "SELECT * FROM medicament m INNER JOIN typemedicament t ON m.type_med_id = t.type_med_id ";
        List<Medicament> medicaments = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                TypeMedicament typeMedicament = new TypeMedicament(resultSet.getInt("type_med_id"), resultSet.getString("type_med_nom"));
                medicaments.add(new Medicament(
                        resultSet.getInt("medi_id"),
                        resultSet.getString("medi_nom"),
                        typeMedicament,
                        resultSet.getDouble("medi_prix"),
                        resultSet.getDate("medi_date_mise_service").toLocalDate(),
                        resultSet.getInt("medi_quantite")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }
}

