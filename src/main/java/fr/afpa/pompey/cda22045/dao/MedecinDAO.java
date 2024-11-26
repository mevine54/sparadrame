package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Medecin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.DatabaseConnection.getConnection;
public class MedecinDAO extends DAO<Medecin> {
    private AdresseDAO adresseDAO = new AdresseDAO();

    @Override
    public Medecin create(Medecin obj) {

//        code SQL-JAVA
        String sql = "INSERT INTO medecin (uti_nom, uti_prenom, adr_id, uti_tel, uti_email, med_num_agreement) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getPrenom());
            statement.setInt(3, obj.getAdresse().getAdrId());
            statement.setString(4, obj.getTelephone());
            statement.setString(5, obj.getEmail());
            statement.setString(6, obj.getNumeroAgrement());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setUserId(generatedKeys.getInt(1));
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
        String sql = "DELETE FROM medecin WHERE med_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            int affectedRows =statement.executeUpdate();
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
    public boolean update(Medecin obj) {
        String sql = "UPDATE medecin SET uti_nom = ?, uti_prenom = ?, adr_id = ?, uti_telephone = ?, uti_email = ?, med_num_agreement = ? WHERE med_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getPrenom());
            statement.setInt(3, obj.getAdresse().getAdrId());
            statement.setString(4, obj.getTelephone());
            statement.setString(5, obj.getEmail());
            statement.setString(6, obj.getNumeroAgrement());
            statement.setInt(7, obj.getUserId());

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
    public Medecin getById(int id) {
        String sql = "SELECT * FROM medecin WHERE med_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Medecin medecin = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer medId = resultSet.getInt("med_id");
                String nom = resultSet.getString("uti_nom");
                String prenom = resultSet.getString("uti_prenom");
                Integer adrId = resultSet.getInt("adr_id");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");
                String numeroAgrement = resultSet.getString("med_num_agreement");

                Adresse adresse = adresseDAO.getById(adrId);

                medecin = new Medecin(medId, nom, prenom, adresse, telephone, email, numeroAgrement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return medecin;
    }

    @Override
    public List<Medecin> getAll() {
        String sql = "SELECT" +
                "m.med_id, " +
                "m.uti_nom, " +
                "m.uti_prenom, " +
                "m.uti_tel, " +
                "m.uti_email, " +
                "m.med_num_agreement " +
                "FROM " +
                "MEDECIN m " +
                "JOIN ADRESSE a ON m.adr_id = a.adr_id, " +
                "JOIN ORDONNANCE o ON m.uti_id = o.uti_id, " +
                "JOIN CLIENT c ON m.med_id = c.med_id";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Medecin> medecins = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer medId = resultSet.getInt("med_id");
                String nom = resultSet.getString("uti_nom");
                String prenom = resultSet.getString("uti_prenom");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");
                String numeroAgrement = resultSet.getString("med_num_agreement");
                Integer adrId = resultSet.getInt("adr_id");

                Adresse adresse = adresseDAO.getById(adrId);

                Medecin medecin = new Medecin(medId, nom, prenom, adresse, telephone, email, numeroAgrement);
                medecins.add(medecin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return medecins;
    }
}

