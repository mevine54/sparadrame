package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Medicament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        statement.setString(5, obj.getTypeMedicament().name());

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
        return List.of();
    }
}
