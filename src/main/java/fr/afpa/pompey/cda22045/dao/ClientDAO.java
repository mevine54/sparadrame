package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.models.Mutuelle;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.DatabaseConnection.getConnection;

public class ClientDAO extends DAO<Client> {

    private AdresseDAO adresseDAO = new AdresseDAO();
    private MutuelleDAO mutuelleDAO = new MutuelleDAO();
    private MedecinDAO medecinTraitantDAO = new MedecinDAO();

    @Override
    public Client create(Client obj) {
        String sqlUtilisateur = "INSERT INTO utilisateur (uti_nom, uti_prenom, uti_tel, uti_email) VALUES (?, ?, ?, ?)";
        String sqlClient = "INSERT INTO client (cli_num_secu_social, cli_date_naissance, uti_id) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statementUtilisateur = null;
        PreparedStatement statementClient = null;

        try {
            connection = DatabaseConnection.getInstanceDB();
            connection.setAutoCommit(false);

            // Vérification des valeurs avant l'insertion
            System.out.println("Nom: " + obj.getUtiNom());
            System.out.println("Prenom: " + obj.getUtiPrenom());
            System.out.println("Telephone: " + obj.getUtiTel());
            System.out.println("Email: " + obj.getUtiEmail());
            System.out.println("NumeroSecuriteSocial: " + obj.getNumeroSecuriteSocial());
            System.out.println("DateNaissance: " + obj.getDateNaissance());

            // Insertion dans UTILISATEUR
            statementUtilisateur = connection.prepareStatement(sqlUtilisateur, PreparedStatement.RETURN_GENERATED_KEYS);
            statementUtilisateur.setString(1, obj.getUtiNom());
            statementUtilisateur.setString(2, obj.getUtiPrenom());
            statementUtilisateur.setString(3, obj.getUtiTel());
            statementUtilisateur.setString(4, obj.getUtiEmail());
            statementUtilisateur.executeUpdate();

            // Récupérer l'uti_id généré
            ResultSet generatedKeys = statementUtilisateur.getGeneratedKeys();
            if (generatedKeys.next()) {
                int utiId = generatedKeys.getInt(1);
                obj.setUtiId(utiId);
                System.out.println("uti_id généré: " + utiId);
            } else {
                throw new SQLException("Échec de la génération de la clé pour utilisateur");
            }

            // Insertion dans CLIENT
            statementClient = connection.prepareStatement(sqlClient, PreparedStatement.RETURN_GENERATED_KEYS);
            statementClient.setString(1, obj.getNumeroSecuriteSocial());
            statementClient.setDate(2, java.sql.Date.valueOf(obj.getDateNaissance()));
            statementClient.setInt(3, obj.getUtiId());
            statementClient.executeUpdate();

            // Récupérer le cli_id généré
            ResultSet generatedKeysClient = statementClient.getGeneratedKeys();
            if (generatedKeysClient.next()) {
                int cliId = generatedKeysClient.getInt(1);
                obj.setCliId(cliId);
                System.out.println("cli_id généré: " + cliId);
            } else {
                throw new SQLException("Échec de la génération de la clé pour client");
            }

            connection.commit();
            return obj;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (statementUtilisateur != null) statementUtilisateur.close();
                if (statementClient != null) statementClient.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
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
        String sql = "UPDATE client SET uti_nom = ?, uti_prenom = ?, adr_id = ?, uti_telephone = ?, uti_email = ?, " +
                "cli_num_secu_social = ?, cli_date_naissance = ?, mut_id = ?, med_id = ? WHERE cli_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false); // désactive l'auto-commit

            statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getUtiNom());
            statement.setString(2, obj.getUtiPrenom());
            statement.setInt(3, obj.getAdresse().getAdrId());
            statement.setString(4, obj.getUtiTel());
            statement.setString(5, obj.getUtiEmail());
            statement.setString(6, obj.getNumeroSecuriteSocial());
            statement.setDate(7, java.sql.Date.valueOf(obj.getDateNaissance()));
            statement.setInt(8, obj.getMutuelle().getMutId());
            statement.setInt(9, obj.getMedecinTraitant().getUtiId());
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
        }
    }

    @Override
    public Client getById(int id) {
        String sql = "SELECT " +
                "c.cli_id, u.uti_nom, u.uti_prenom, u.uti_tel, u.uti_email, " +
                "c.cli_num_secu_social, c.cli_date_naissance, " +
                "a.adr_id, a.adr_rue, a.adr_code_postal, a.adr_ville, " +
                "mu.mut_id, mu.mut_nom, mu.mut_tel, mu.mut_email, mu.mut_departement, mu.mut_taux_prise_en_charge " +
                "FROM client c " +
                "JOIN utilisateur u ON c.uti_id = u.uti_id " +
                "JOIN posseder p ON u.uti_id = p.uti_id " +
                "JOIN adresse a ON p.adr_id = a.adr_id " +
                "LEFT JOIN adherer ad ON c.cli_id = ad.cli_id " +
                "LEFT JOIN mutuelle mu ON ad.mut_id = mu.mut_id " +
                "WHERE c.cli_id = ?";

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer cliId = resultSet.getInt("cli_id");
                    String nom = resultSet.getString("uti_nom");
                    String prenom = resultSet.getString("uti_prenom");
                    String telephone = resultSet.getString("uti_tel");
                    String email = resultSet.getString("uti_email");
                    String numeroSecuriteSocial = resultSet.getString("cli_num_secu_social");
                    LocalDate dateNaissance = resultSet.getDate("cli_date_naissance").toLocalDate();

                    // Adresse
                    Adresse adresse = new Adresse(
                            resultSet.getInt("adr_id"),
                            resultSet.getString("adr_rue"),
                            resultSet.getString("adr_code_postal"),
                            resultSet.getString("adr_ville")
                    );

                    // Mutuelle
                    Mutuelle mutuelle = new Mutuelle(
                            resultSet.getInt("mut_id"),
                            resultSet.getString("mut_nom"),
                            adresse, // Adresse de la mutuelle si elle existe dans votre base
                            resultSet.getString("mut_tel"),
                            resultSet.getString("mut_email"),
                            resultSet.getString("mut_departement"),
                            resultSet.getDouble("mut_taux_prise_en_charge")
                    );

                    return new Client(cliId, nom, prenom, adresse, telephone, email, numeroSecuriteSocial,
                            dateNaissance, mutuelle, null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        String sql = "SELECT " +
                "c.cli_id, u.uti_nom, u.uti_prenom, u.uti_tel, u.uti_email, " +
                "c.cli_num_secu_social, c.cli_date_naissance, " +
                "a.adr_id, a.adr_rue, a.adr_code_postal, a.adr_ville, " +
                "mu.mut_id, mu.mut_nom, mu.mut_tel, mu.mut_email, mu.mut_departement, mu.mut_taux_prise_en_charge " +
                "FROM client c " +
                "JOIN utilisateur u ON c.uti_id = u.uti_id " +
                "LEFT JOIN posseder p ON u.uti_id = p.uti_id " +
                "LEFT JOIN adresse a ON p.adr_id = a.adr_id " +
                "LEFT JOIN adherer ad ON c.cli_id = ad.cli_id " +
                "LEFT JOIN mutuelle mu ON ad.mut_id = mu.mut_id";

        List<Client> clients = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer cliId = resultSet.getInt("cli_id");
                String nom = resultSet.getString("uti_nom");
                String prenom = resultSet.getString("uti_prenom");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");
                String numeroSecuriteSocial = resultSet.getString("cli_num_secu_social");
                LocalDate dateNaissance = resultSet.getDate("cli_date_naissance").toLocalDate();

                // Adresse
                Adresse adresse = new Adresse(
                        resultSet.getInt("adr_id"),
                        resultSet.getString("adr_rue"),
                        resultSet.getString("adr_code_postal"),
                        resultSet.getString("adr_ville")
                );

                // Mutuelle (gestion du cas où l'adresse est null)
                Mutuelle mutuelle = null;
                if (resultSet.getInt("mut_id") != 0) { // Vérifie si une mutuelle existe
                    mutuelle = new Mutuelle(
                            resultSet.getInt("mut_id"),
                            resultSet.getString("mut_nom"),
                            adresse, // Adresse de la mutuelle non prise en charge dans cette requête
                            resultSet.getString("mut_tel"),
                            resultSet.getString("mut_email"),
                            resultSet.getString("mut_departement"),
                            resultSet.getDouble("mut_taux_prise_en_charge")
                    );
                }

                // Ajouter un nouveau client
                clients.add(new Client(cliId, nom, prenom, adresse, telephone, email, numeroSecuriteSocial,
                        dateNaissance, mutuelle, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public Client findByName(String nom, String prenom) {
        String query = "SELECT " +
                "c.cli_id, u.uti_nom, u.uti_prenom, u.uti_tel, u.uti_email, " +
                "c.cli_num_secu_social, c.cli_date_naissance, " +
                "a.adr_id, a.adr_rue, a.adr_code_postal, a.adr_ville, " +
                "mu.mut_id, mu.mut_nom, mu.mut_tel, mu.mut_email, mu.mut_departement, mu.mut_taux_prise_en_charge " +
                "FROM client c " +
                "JOIN utilisateur u ON c.uti_id = u.uti_id " +
                "JOIN posseder p ON u.uti_id = p.uti_id " +
                "JOIN adresse a ON p.adr_id = a.adr_id " +
                "LEFT JOIN adherer ad ON c.cli_id = ad.cli_id " +
                "LEFT JOIN mutuelle mu ON ad.mut_id = mu.mut_id " +
                "WHERE u.uti_nom = ? AND u.uti_prenom = ?";
        Client client = null;

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nom);
            statement.setString(2, prenom);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Integer cliId = resultSet.getInt("cli_id");
                String telephone = resultSet.getString("uti_tel");
                String email = resultSet.getString("uti_email");
                String numeroSecuriteSocial = resultSet.getString("cli_num_secu_social");
                LocalDate dateNaissance = resultSet.getDate("cli_date_naissance").toLocalDate();

                // Adresse
                Adresse adresse = new Adresse(
                        resultSet.getInt("adr_id"),
                        resultSet.getString("adr_rue"),
                        resultSet.getString("adr_code_postal"),
                        resultSet.getString("adr_ville")
                );

                // Mutuelle
                Mutuelle mutuelle = null;
                if (resultSet.getInt("mut_id") != 0) { // Vérifie si une mutuelle existe
                    mutuelle = new Mutuelle(
                            resultSet.getInt("mut_id"),
                            resultSet.getString("mut_nom"),
                            adresse, // Adresse de la mutuelle non prise en charge dans cette requête
                            resultSet.getString("mut_tel"),
                            resultSet.getString("mut_email"),
                            resultSet.getString("mut_departement"),
                            resultSet.getDouble("mut_taux_prise_en_charge")
                    );
                }

                client = new Client(cliId, nom, prenom, adresse, telephone, email, numeroSecuriteSocial,
                        dateNaissance, mutuelle, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }
}
