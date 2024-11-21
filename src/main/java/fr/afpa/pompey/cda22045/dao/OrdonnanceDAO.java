package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.models.Ordonnance;
import fr.afpa.pompey.cda22045.models.Specialiste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.Singleton.getConnection;

public class OrdonnanceDAO extends DAO<Ordonnance> {
    private MedecinDAO medecinDAO = new MedecinDAO();
    private ClientDAO clientDAO = new ClientDAO();
    private SpecialisteDAO specialisteDAO = new SpecialisteDAO();


    @Override
    public Ordonnance create(Ordonnance obj) {
        String sql = "INSERT INTO ordonnance (ord_date, ord_nom_client, ord_nom_medecin, ord_nom_specialiste, spe_uti_id, spe_id, uti_id, cli_uti_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, java.sql.Date.valueOf(obj.getDate()));
            statement.setString(2, obj.getPatient().getNom());
            statement.setString(3, obj.getMedecin().getNom());
            statement.setString(4, obj.getSpecialiste().getNom());
            statement.setInt(5, obj.getSpecialiste().getUserId()); // spe_uti_id
            statement.setInt(6, obj.getSpecialiste().getTypeSpecialiste().getTypeId()); // spe_id
            statement.setInt(7, obj.getMedecin().getUserId()); // uti_id
            statement.setInt(8, obj.getPatient().getUserId()); // cli_uti_id

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setOrdId(generatedKeys.getInt(1));
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
        String sql = "DELETE FROM ordonnance WHERE ord_id = ?";
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
    public boolean update(Ordonnance obj) {
        String sql = "UPDATE ordonnance SET ord_date = ?, ord_nom_client = ?, ord_nom_medecin = ?, ord_nom_specialiste = ?, spe_uti_id = ?, spe_id = ?, uti_id = ?, cli_uti_id = ? WHERE ord_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(sql);
            statement.setDate(1, java.sql.Date.valueOf(obj.getDate()));
            statement.setString(2, obj.getPatient().getNom());
            statement.setString(3, obj.getMedecin().getNom());
            statement.setString(4, obj.getSpecialiste().getNom());
            statement.setInt(5, obj.getSpecialiste().getUserId()); // spe_uti_id
            statement.setInt(6, obj.getSpecialiste().getTypeSpecialiste().getTypeId()); // spe_id
            statement.setInt(7, obj.getMedecin().getUserId()); // uti_id
            statement.setInt(8, obj.getPatient().getUserId()); // cli_uti_id
            statement.setInt(9, obj.getOrdId()); // ord_id

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
    public Ordonnance getById(int id) {
        String sql = "SELECT * FROM ordonnance WHERE ord_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Ordonnance ordonnance = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer ordId = resultSet.getInt("ord_id");
                LocalDate ordDate = resultSet.getDate("ord_date").toLocalDate();
                String ordNomPatient = resultSet.getString("ord_nom_client");
                String ordNomMedecin = resultSet.getString("ord_nom_medecin");
                String ordNomSpecialiste = resultSet.getString("ord_nom_specialiste");
                Integer specialisteUserId = resultSet.getInt("spe_uti_id");
                Integer typeSpecialisteId = resultSet.getInt("spe_id");
                Integer medecinUserId = resultSet.getInt("uti_id");
                Integer patientUserId = resultSet.getInt("cli_uti_id");

                // Assuming you have DAOs for Medecin, Client, and Specialiste
                Medecin medecin = medecinDAO.getById(medecinUserId);
                Client patient = clientDAO.getById(patientUserId);
                Specialiste specialiste = specialisteDAO.getById(specialisteUserId);

                ordonnance = new Ordonnance(ordId, ordDate, medecin, patient, null, specialiste);
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
        return ordonnance;
    }

    @Override
    public List<Ordonnance> getAll() {
        String sql = "SELECT * FROM ordonnance";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ordonnance> ordonnances = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer ordId = resultSet.getInt("ord_id");
                LocalDate ordDate = resultSet.getDate("ord_date").toLocalDate();
                String ordNomPatient = resultSet.getString("ord_nom_client");
                String ordNomMedecin = resultSet.getString("ord_nom_medecin");
                String ordNomSpecialiste = resultSet.getString("ord_nom_specialiste");
                Integer specialisteUserId = resultSet.getInt("spe_uti_id");
                Integer typeSpecialisteId = resultSet.getInt("spe_id");
                Integer medecinUserId = resultSet.getInt("uti_id");
                Integer patientUserId = resultSet.getInt("cli_uti_id");

                // Assuming you have DAOs for Medecin, Client, and Specialiste
                Medecin medecin = medecinDAO.getById(medecinUserId);
                Client patient = clientDAO.getById(patientUserId);
                Specialiste specialiste = specialisteDAO.getById(specialisteUserId);

                Ordonnance ordonnance = new Ordonnance(ordId, ordDate, medecin, patient, null, specialiste);
                ordonnances.add(ordonnance);
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
        return ordonnances;
    }
}
