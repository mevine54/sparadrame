package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static fr.afpa.pompey.cda22045.Singleton.getConnection;

public class ClientDAO extends DAO<Client> {

    @Override
    public Client create(Client obj) {

        // code SQL - JAVA
        String sql = "INSERT INTO client (nom, prenom, adresse_id, telephone, email, numeroSecuriteSocial, dateNaissance, mutuelle_id, medecinTraitant_id ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getPrenom());
            statement.setInt(3, obj.getAdresse().getId());
            statement.setString(4, obj.getTelephone());
            statement.setString(5, obj.getEmail());
            statement.setString(6, obj.getNumeroSecuriteSocial());
            statement.setString(7, obj.getDateNaissance());
            statement.setInt(8, obj.getMutuelle().getId());
            statement.setInt(9, obj.getMedecinTraitant().getMedId());


            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setCliId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean update(Client obj) {
        return false;
    }

    @Override
    public Client getById(int id) {
        return null;
    }

    @Override
    public List<Client> getAll() {

        // code SQL - JAVA
        return List.of();
    }
}
