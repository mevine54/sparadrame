package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.models.Mutuelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.Singleton.getConnection;

public class ClientDAO extends DAO<Client> {

    private AdresseDAO adresseDAO = new AdresseDAO();
    private MutuelleDAO mutuelleDAO = new MutuelleDAO();
    private MedecinDAO medecinTraitantDAO = new MedecinDAO();

    @Override
    public Client create(Client obj) {

        // code SQL - JAVA
        String sql = "INSERT INTO client (uti_nom, uti_prenom, adr_id, uti_tel, uti_email, cli_num_secu_social, " +
                "cli_date_naissance, mut_id, med_id ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false); // désactive l'auto-commit

            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setString(1, obj.getNom());
                statement.setString(2, obj.getPrenom());
                statement.setInt(3, obj.getAdresse().getAdrId());
                statement.setString(4, obj.getTelephone());
                statement.setString(5, obj.getEmail());
                statement.setString(6, obj.getNumeroSecuriteSocial());
                statement.setDate(7, java.sql.Date.valueOf(obj.getDateNaissance()));
                statement.setInt(8, obj.getMutuelle().getMutId());
                statement.setInt(9, obj.getMedecinTraitant().getUserId());

                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            obj.setCliId(generatedKeys.getInt(1));
                        }
                    }
                }
                connection.commit(); // valide la transaction
            } catch (SQLException e){
                if (connection != null) {
                    try {
                        connection.rollback(); // annule la transacton en cas d'erreur
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
//                if (connection != null) {
//                    try {
//                     connection.close();
//                    } catch (SQLException e) {
//                    e.printStackTrace();
//                }
            }

        return obj;
    }


    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM client WHERE cli_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false); // Désactive l'auto-commit

            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            connection.commit(); // valide la transaction

            return affectedRows > 0;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // annule la transaction en cas d'erreur
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
    public boolean update(Client obj) {
        String sql = "UPDATE client SET uti_nom = ?, uti_prenom = ?, adr_id = ?, uti_telephone = ?, uti_email = ?, cli_num_secu_social = ?, cli_date_naissance = ?, mut_id = ?, med_id = ? WHERE cli_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false); // désactive l'auto-commit

            statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getPrenom());
            statement.setInt(3, obj.getAdresse().getAdrId());
            statement.setString(4, obj.getTelephone());
            statement.setString(5, obj.getEmail());
            statement.setString(6, obj.getNumeroSecuriteSocial());
            statement.setDate(7, java.sql.Date.valueOf(obj.getDateNaissance()));
            statement.setInt(8, obj.getMutuelle().getMutId());
            statement.setInt(9, obj.getMedecinTraitant().getUserId());
            statement.setInt(10, obj.getCliId());

            int affectedRows = statement.executeUpdate();
            connection.commit(); // valide la transaction

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
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    @Override
    public Client getById(int id) {
        String sql = "SELECT * FROM client WHERE cli_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {

//                client.setCliId(resultSet.getInt("cli_id"));
                Integer cliId = resultSet.getInt("cli_id");
//                client.setNom(resultSet.getString("nom"));
                String nom = resultSet.getString("uti_nom");
//                client.setPrenom(resultSet.getString("prenom"));
                String prenom = resultSet.getString("uti_prenom");
//                client.setTelephone(resultSet.getString("telephone"));
                String telephone = resultSet.getString("uti_tel");
//                client.setEmail(resultSet.getString("email"));
                String email = resultSet.getString("uti_email");
//                client.setNumeroSecuriteSocial(resultSet.getString("numeroSecuriteSocial"));
                String numeroSecuriteSocial = resultSet.getString("cli_num_secu_social");
//                client.setDateNaissance(resultSet.getString("dateNaissance"));
                LocalDate dateNaissance = resultSet.getDate("cli_date_naissance").toLocalDate();
//                client.setAdresse(new Adresse(resultSet.getInt("adresse_id")));
                Integer adrId = resultSet.getInt("adr_id");
//                client.setMutuelle(new Mutuelle(resultSet.getInt("mut_id")));
                Integer mutId = resultSet.getInt("mut_id");
//                client.setMedecinTraitant(new MedecinTraitant(resultSet.getInt("med_id")));
                Integer medId = resultSet.getInt("med_id");

                Adresse adresse = adresseDAO.getById(adrId);
                Mutuelle mutuelle = mutuelleDAO.getById(mutId);
                Medecin medecin = medecinTraitantDAO.getById(medId);

                client = new Client(cliId, nom, prenom, adresse, telephone, email, numeroSecuriteSocial, dateNaissance, mutuelle, medecin);
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
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        return client;
    }

    @Override
    public List<Client> getAll() {
        // code SQL - JAVA
        String sql = "SELECT " +
                "c.cli_id, " +
                "c.uti_nom AS client_nom, " +
                "c.uti_prenom AS client_prenom, " +
                "c.uti_tel AS client_tel, " +
                "c.uti_email AS client_email, " +
                "c.cli_num_secu_social, " +
                "c.cli_date_naissance, " +
                "a.adr_id AS adresse_id, " +
                "mu.mut_id AS mutuelle_id, " +
                "m.med_id AS medecin_id " +
                "FROM " +
                "CLIENT c " +
                "JOIN MEDECIN m ON c.med_id = m.med_id " +
                "JOIN ORDONNANCE o ON m.uti_id = o.uti_id " +
                "JOIN MUTUELLE mu ON c.mut_id = mu.mut_id " +
                "JOIN SPECIALISTE s ON m.med_id = s.med_id " +
                "JOIN ADRESSE a ON m.adr_id = a.adr_id";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Client> clients = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer cliId = resultSet.getInt("cli_id");
                String nom = resultSet.getString("uti_nom");
                String prenom = resultSet.getString("uti_prenom");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");
                String numeroSecuriteSociale = resultSet.getString("cli_num_secu_social");
                LocalDate dateNaissance = resultSet.getDate("cli_date_naissance").toLocalDate();
                Integer adrId = resultSet.getInt("adr_id");
                Integer mutId = resultSet.getInt("mut_id");
                Integer medId = resultSet.getInt("med_id");

                Adresse adresse = adresseDAO.getById(adrId);
                Mutuelle mutuelle = mutuelleDAO.getById(mutId);
                Medecin medecin = medecinTraitantDAO.getById(medId);

                Client client = new Client(cliId, nom, prenom, adresse, telephone, email, numeroSecuriteSociale, dateNaissance, mutuelle, medecin);
                clients.add(client);
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
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        return clients;
    }
}
