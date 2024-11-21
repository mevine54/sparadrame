package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Specialiste;
import fr.afpa.pompey.cda22045.enums.Specialite;
import fr.afpa.pompey.cda22045.models.TypeSpecialiste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.Singleton.getConnection;

public class SpecialisteDAO extends DAO<Specialiste> {
    private AdresseDAO adresseDAO = new AdresseDAO();
    private TypeSpecialisteDAO typeSpecialisteDAO = new TypeSpecialisteDAO();

    @Override
    public Specialiste create(Specialiste obj) {
        String sql = "INSERT INTO specialiste (uti_nom, uti_prenom, adr_id, uti_tel, uti_email, numeroAgrement, specialite) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            statement.setString(7, obj.getTypeSpecialiste().getTypeNom()); // Convertir l'objet Specialite en String

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
        String sql = "DELETE FROM specialiste WHERE spe_id = ?";
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
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean update(Specialiste obj) {
        String sql = "UPDATE specialiste SET uti_nom = ?, uti_prenom = ?, adr_id = ?, uti_tel = ?, uti_email = ?, numeroAgrement = ?, specialite = ? WHERE spe_id = ?";
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
            statement.setString(7, obj.getTypeSpecialiste().getTypeNom()); // Convertir l'objet Specialite en String
            statement.setInt(8, obj.getUserId());

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
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Specialiste getById(int id) {
        String sql = "SELECT * FROM specialiste WHERE spe_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Specialiste specialiste = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("spe_id");
                String nom = resultSet.getString("uti_nom");
                String prenom = resultSet.getString("uti_prenom");
                Integer adrId = resultSet.getInt("adr_id");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");
                String numeroAgrement = resultSet.getString("numeroAgrement");
                String specialiteName = resultSet.getString("specialite");
                Integer typeId = resultSet.getInt("typeSpecialiste");

                Adresse adresse = adresseDAO.getById(adrId);
                TypeSpecialiste typeSpecialiste = typeSpecialisteDAO.getById(typeId);
//                Specialite specialite = Specialite.valueOf(specialiteName); // Convertir la chaîne de caractères en objet Specialite

                specialiste = new Specialiste(userId, nom, prenom, adresse, telephone, email, numeroAgrement, typeSpecialiste);

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
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return specialiste;
    }

    @Override
    public List<Specialiste> getAll() {
        String sql = "SELECT " +
                "s.spe_id AS specialite_id, " +
                "s.spe_nom AS specialite_nom, " +
                "s.spe_uti_id AS specialite_utilisateur_id, " +
                "ts.type_id AS type_specialiste_id, " +
                "ts.type_nom AS type_specialite_nom, " +
                "m.med_id AS medecin_id, " +
                "m.med_num_agreement AS med_num_agreement, " +
                "a.adr_id AS adresse_id, " +
                "c.cli_uti_id AS client_utilisateur_id, " +
                "u.uti_id AS utilisateur_id, " +
                "u.uti_nom AS utilisateur_nom, " +
                "u.uti_prenom AS utilisateur_prenom, " +
                "u.uti_tel AS utilisateur_telephone, " +
                "u.uti_email AS utilisateur_email, " +
                "o.ord_id AS ordonnance_id, " +
                "o.ord_date AS ordonnance_date, " +
                "o.ord_nom_client, " +
                "o.ord_nom_medecin, " +
                "o.ord_nom_specialiste " +
                "FROM" +
                "SPECIALISTE s " +
                "JOIN TYPESPECIALISTE ts ON s.type_id = ts.type_id, " +
                "JOIN MEDECIN m ON s.med_id = m.med_id, " +
                "JOIN ADRESSE a ON m.adr_id = a.adr_id, " +
                "JOIN CLIENT c ON m.med_id = c.med_id, " +
                "JOIN UTILISATEUR u ON m.uti_id = u.uti_id, " +
                "JOIN ORDONNANCE o ON m.uti_id = o.uti_id";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Specialiste> specialistes = new ArrayList<Specialiste>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer userId = resultSet.getInt("spe_id");
                String nom = resultSet.getString("uti_nom");
                String prenom = resultSet.getString("uti_prenom");
                Integer adrId = resultSet.getInt("adr_id");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");
                String numeroAgrement = resultSet.getString("med_num_agreement");
                String specialiteName = resultSet.getString("specialite");
                Integer typeId = resultSet.getInt("typeSpecialiste");

                Adresse adresse = adresseDAO.getById(adrId);
                TypeSpecialiste typeSpecialiste = typeSpecialisteDAO.getById(typeId);

                 Specialiste specialiste = new Specialiste(userId, nom, prenom, adresse, telephone, email, numeroAgrement, typeSpecialiste );
                 specialistes.add(specialiste);
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
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return specialistes;
    }
}
