package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.DatabaseConnection.getConnection;

public class UtilisateurDAO extends DAO<Utilisateur> {
    private AdresseDAO adresseDAO = new AdresseDAO();
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    public Utilisateur create(Utilisateur obj) {
        String sql = "INSERT INTO utilisateur (uti_nom, uti_prenom, adr_id, uti_tel, uti_email) VALUES (?,?,?,?,?)";
        Connection connection = getConnection();
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

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
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
        }
        return obj;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM utilisateur WHERE uti_id = ?";
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
    public boolean update(Utilisateur obj) {
        String sql = "UPDATE utilisateur SET uti_nom = ?, uti_prenom = ?, adr_id = ?, uti_tel = ?, uti_email = ? WHERE uti_id = ?";
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
            statement.setInt(6, obj.getUserId());

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
    public Utilisateur getById(int id) {
        String sql = "SELECT * FROM utilisateur WHERE uti_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {

                Integer userId = resultSet.getInt("uti_id");
                String nom = resultSet.getString("uti_nom");
                String prenom = resultSet.getString("uti_prenom");
                Integer adrId = resultSet.getInt("adr_id");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");

                Adresse adresse = adresseDAO.getById(adrId);

                utilisateur = new Utilisateur(userId, nom, prenom, adresse, telephone, email);
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
        return utilisateur;
    }

    @Override
    public List<Utilisateur> getAll() {
        String sql = "SELECT u.uti_id, u.uti_nom, u.uti_prenom, u.uti_tel, u.uti_email, " +
                "a.adr_id, a.adr_rue, a.adr_code_postal, a.adr_ville " +
                "FROM utilisateur u " +
                "LEFT JOIN posseder p ON u.uti_id = p.uti_id " +
                "LEFT JOIN adresse a ON p.adr_id = a.adr_id";

        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer userId = resultSet.getInt("uti_id");
                String nom = resultSet.getString("uti_nom");
                String prenom = resultSet.getString("uti_prenom");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");

                Adresse adresse = new Adresse(
                        resultSet.getInt("adr_id"),
                        resultSet.getString("adr_rue"),
                        resultSet.getString("adr_code_postal"),
                        resultSet.getString("adr_ville")
                );

                utilisateurs.add(new Utilisateur(userId, nom, prenom, adresse, telephone, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }
}