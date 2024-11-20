package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Mutuelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static fr.afpa.pompey.cda22045.Singleton.getConnection;

public class MutuelleDAO extends DAO<Mutuelle> {

    @Override
    public Mutuelle create(Mutuelle obj) {

        String sql = "INSERT INTO mutuelle (mut_nom, adr_id, mut_tel, mut_email, mut_departement, mut_taux_prise_en_charge) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getNom());
            statement.setInt(2, obj.getAdresse().getAdrId());
            statement.setString(3, obj.getTelephone());
            statement.setString(4, obj.getEmail());
            statement.setString(5, obj.getDepartement());
            statement.setDouble(6, obj.getTauxPriseEnCharge());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setMutId(generatedKeys.getInt(1));
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
        }
        return obj;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM mutuelle WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            connection.commit();

            return affectedRows > 0;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean update(Mutuelle obj) {
        String sql = "UPDATE mutuelle SET mut_nom = ?, mut_tel = ?, mut_email = ?, mut_departement = ?, mut_taux_prise_en_charge = ? WHERE mut_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getTelephone());
            statement.setString(3, obj.getEmail());
            statement.setString(4, obj.getDepartement());
            statement.setDouble(5, obj.getTauxPriseEnCharge());
            statement.setInt(6, obj.getMutId());

            int affectedRows = statement.executeUpdate();
            connection.commit();

            return affectedRows > 0;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Mutuelle getById(int id) {
        return null;
    }

    @Override
    public List<Mutuelle> getAll() {
        return List.of();
    }
}
