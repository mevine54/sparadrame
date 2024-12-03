package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinDAO extends DAO<Medecin> {

    @Override
    public Medecin create(Medecin obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Le médecin ne peut pas être null.");
        }

        // Validation du numéro d'agrément
        if (!obj.getMedNumAgreement().matches("\\d{8}")) {
            throw new IllegalArgumentException("Le numéro d'agrément doit contenir 8 chiffres.");
        }

        String sqlUtilisateur = "INSERT INTO utilisateur (uti_nom, uti_prenom, uti_tel, uti_email) VALUES (?, ?, ?, ?)";
        String sqlAdresse = "INSERT INTO adresse (adr_rue, adr_code_postal, adr_ville) VALUES (?, ?, ?)";
        String sqlPosseder = "INSERT INTO posseder (uti_id, adr_id, type_possession) VALUES (?, ?, ?)";
        String sqlMedecin = "INSERT INTO medecin (uti_id, med_num_agreement) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getInstanceDB()) {
            connection.setAutoCommit(false);

            try {
                // Vérifier si l'utilisateur existe déjà
                Integer existingUserId = getUserIdIfExists(connection, obj.getUtiEmail());
                if (existingUserId == null) {
                    // Insertion dans utilisateur
                    try (PreparedStatement statementUtilisateur = connection.prepareStatement(sqlUtilisateur, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        statementUtilisateur.setString(1, obj.getUtiNom());
                        statementUtilisateur.setString(2, obj.getUtiPrenom());
                        statementUtilisateur.setString(3, obj.getUtiTel());
                        statementUtilisateur.setString(4, obj.getUtiEmail());
                        statementUtilisateur.executeUpdate();

                        try (ResultSet generatedKeys = statementUtilisateur.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                obj.setUtiId(generatedKeys.getInt(1));
                            } else {
                                throw new SQLException("Échec de la création de l'utilisateur, aucun ID généré.");
                            }
                        }
                    }
                } else {
                    obj.setUtiId(existingUserId);
                }

                // Vérifier si l'adresse existe déjà
                Integer existingAdresseId = getAdresseIdIfExists(connection, obj.getAdresse());
                if (existingAdresseId == null) {
                    // Insertion dans adresse
                    try (PreparedStatement statementAdresse = connection.prepareStatement(sqlAdresse, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        statementAdresse.setString(1, obj.getAdresse().getAdrRue());
                        statementAdresse.setString(2, obj.getAdresse().getAdrCodePostal());
                        statementAdresse.setString(3, obj.getAdresse().getAdrVille());
                        statementAdresse.executeUpdate();

                        try (ResultSet generatedKeys = statementAdresse.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                obj.getAdresse().setAdrId(generatedKeys.getInt(1));
                            } else {
                                throw new SQLException("Échec de la création de l'adresse, aucun ID généré.");
                            }
                        }
                    }
                } else {
                    obj.getAdresse().setAdrId(existingAdresseId);
                }

                // Insertion dans posseder
                try (PreparedStatement statementPosseder = connection.prepareStatement(sqlPosseder)) {
                    statementPosseder.setInt(1, obj.getUtiId());
                    statementPosseder.setInt(2, obj.getAdresse().getAdrId());
                    statementPosseder.setString(3, "Résidentiel"); // Exemple de type de possession
                    statementPosseder.executeUpdate();
                }

                // Vérifier si le médecin existe déjà
                if (isMedecinExists(connection, obj.getUtiId())) {
                    throw new SQLException("Le médecin avec l'ID utilisateur " + obj.getUtiId() + " existe déjà.");
                }

                // Insertion dans médecin
                try (PreparedStatement statementMedecin = connection.prepareStatement(sqlMedecin)) {
                    statementMedecin.setInt(1, obj.getUtiId());
                    statementMedecin.setString(2, obj.getMedNumAgreement());
                    statementMedecin.executeUpdate();
                }

                connection.commit();
                return obj;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer getUserIdIfExists(Connection connection, String email) throws SQLException {
        String sql = "SELECT uti_id FROM utilisateur WHERE uti_email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("uti_id");
                }
            }
        }
        return null;
    }

    private Integer getAdresseIdIfExists(Connection connection, Adresse adresse) throws SQLException {
        String sql = "SELECT adr_id FROM adresse WHERE adr_rue = ? AND adr_code_postal = ? AND adr_ville = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, adresse.getAdrRue());
            statement.setString(2, adresse.getAdrCodePostal());
            statement.setString(3, adresse.getAdrVille());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("adr_id");
                }
            }
        }
        return null;
    }

    private boolean isMedecinExists(Connection connection, int utiId) throws SQLException {
        String sql = "SELECT 1 FROM medecin WHERE uti_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, utiId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM medecin WHERE med_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Medecin obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Le médecin ne peut pas être null.");
        }

        // Validation du numéro d'agrément
        if (!obj.getMedNumAgreement().matches("\\d{8}")) {
            throw new IllegalArgumentException("Le numéro d'agrément doit contenir 8 chiffres.");
        }

        String sqlUtilisateur = "UPDATE utilisateur SET uti_nom = ?, uti_prenom = ?, uti_tel = ?, uti_email = ? WHERE uti_id = ?";
        String sqlAdresse = "UPDATE adresse SET adr_rue = ?, adr_code_postal = ?, adr_ville = ? WHERE adr_id = ?";
        String sqlMedecin = "UPDATE medecin SET med_num_agreement = ? WHERE uti_id = ?";

        try (Connection connection = DatabaseConnection.getInstanceDB()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statementUtilisateur = connection.prepareStatement(sqlUtilisateur);
                 PreparedStatement statementAdresse = connection.prepareStatement(sqlAdresse);
                 PreparedStatement statementMedecin = connection.prepareStatement(sqlMedecin)) {

                // Mise à jour de l'utilisateur
                statementUtilisateur.setString(1, obj.getUtiNom());
                statementUtilisateur.setString(2, obj.getUtiPrenom());
                statementUtilisateur.setString(3, obj.getUtiTel());
                statementUtilisateur.setString(4, obj.getUtiEmail());
                statementUtilisateur.setInt(5, obj.getUtiId());
                statementUtilisateur.executeUpdate();

                // Mise à jour de l'adresse
                statementAdresse.setString(1, obj.getAdresse().getAdrRue());
                statementAdresse.setString(2, obj.getAdresse().getAdrCodePostal());
                statementAdresse.setString(3, obj.getAdresse().getAdrVille());
                statementAdresse.setInt(4, obj.getAdresse().getAdrId());
                statementAdresse.executeUpdate();

                // Mise à jour du médecin
                statementMedecin.setString(1, obj.getMedNumAgreement());
                statementMedecin.setInt(2, obj.getUtiId());
                statementMedecin.executeUpdate();

                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Medecin getById(int id) {
        String sql = "SELECT * FROM medecin m JOIN utilisateur u ON m.uti_id = u.uti_id LEFT JOIN posseder p ON u.uti_id = p.uti_id LEFT JOIN adresse a ON p.adr_id = a.adr_id WHERE m.uti_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Adresse adresse = new Adresse(
                            resultSet.getInt("adr_id"),
                            resultSet.getString("adr_rue"),
                            resultSet.getString("adr_code_postal"),
                            resultSet.getString("adr_ville")
                    );
                    return new Medecin(
                            resultSet.getInt("med_id"),
                            resultSet.getString("uti_nom"),
                            resultSet.getString("uti_prenom"),
                            adresse,
                            resultSet.getString("uti_tel"),
                            resultSet.getString("uti_email"),
                            resultSet.getString("med_num_agreement")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medecin> getAll() {
        String sql = "SELECT * FROM medecin m JOIN utilisateur u ON m.uti_id = u.uti_id LEFT JOIN posseder p ON u.uti_id = p.uti_id LEFT JOIN adresse a ON p.adr_id = a.adr_id";
        List<Medecin> medecins = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Adresse adresse = new Adresse(
                        resultSet.getInt("adr_id"),
                        resultSet.getString("adr_rue"),
                        resultSet.getString("adr_code_postal"),
                        resultSet.getString("adr_ville")
                );
                Medecin medecin = new Medecin(
                        resultSet.getInt("med_id"),
                        resultSet.getString("uti_nom"),
                        resultSet.getString("uti_prenom"),
                        adresse,
                        resultSet.getString("uti_tel"),
                        resultSet.getString("uti_email"),
                        resultSet.getString("med_num_agreement")
                );
                medecins.add(medecin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecins;
    }
}
