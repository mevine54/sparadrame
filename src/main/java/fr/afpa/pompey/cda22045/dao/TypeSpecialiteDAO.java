package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.TypeSpecialite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static fr.afpa.pompey.cda22045.utilities.DatabaseConnection.getConnection;

public class TypeSpecialiteDAO {

    /**
     * Récupère un TypeSpecialite par son ID.
     *
     * @param id l'ID du TypeSpecialite à rechercher.
     * @return un objet TypeSpecialite si trouvé, sinon null.
     */
    public TypeSpecialite getById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID doit être supérieur à 0.");
        }

        String sql = "SELECT tsTypeId, tsTypeNom FROM typespecialiste WHERE tsTypeId = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new TypeSpecialite(
                            resultSet.getInt("tsTypeId"),
                            resultSet.getString("tsTypeNom")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du TypeSpecialite avec ID " + id);
            e.printStackTrace();
        }
        return null; // Défensif : retourne null si aucune correspondance
    }

    /**
     * Récupère tous les TypeSpecialite.
     *
     * @return une liste contenant tous les objets TypeSpecialite.
     */
    public List<TypeSpecialite> getAll() {
        String sql = "SELECT tsTypeId, tsTypeNom FROM typespecialiste";
        List<TypeSpecialite> typeSpecialites = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                typeSpecialites.add(new TypeSpecialite(
                        resultSet.getInt("tsTypeId"),
                        resultSet.getString("tsTypeNom")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les TypeSpecialite.");
            e.printStackTrace();
        }

        return typeSpecialites;
    }

    /**
     * Ajoute un nouveau TypeSpecialite.
     *
     * @param typeSpecialite l'objet TypeSpecialite à insérer.
     * @return true si l'insertion est réussie, false sinon.
     */
    public boolean create(TypeSpecialite typeSpecialite) {
        if (typeSpecialite == null) {
            throw new IllegalArgumentException("Le TypeSpecialite à insérer ne peut pas être null.");
        }

        String sql = "INSERT INTO typespecialiste (tsTypeId, tsTypeNom) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, typeSpecialite.getTsTypeId());
            statement.setString(2, typeSpecialite.getTsTypeNom());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0; // Retourne true si l'insertion a réussi
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du TypeSpecialite : " + typeSpecialite);
            e.printStackTrace();
        }
        return false; // Défensif : retourne false en cas d'échec
    }

    /**
     * Supprime un TypeSpecialite par son ID.
     *
     * @param id l'ID du TypeSpecialite à supprimer.
     * @return true si la suppression est réussie, false sinon.
     */
    public boolean delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("L'ID doit être supérieur à 0 pour la suppression.");
        }

        String sql = "DELETE FROM typespecialiste WHERE tsTypeId = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0; // Retourne true si la suppression a réussi
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du TypeSpecialite avec ID " + id);
            e.printStackTrace();
        }
        return false; // Défensif : retourne false en cas d'échec
    }

    /**
     * Met à jour un TypeSpecialite existant.
     *
     * @param typeSpecialite l'objet TypeSpecialite contenant les nouvelles données.
     * @return true si la mise à jour est réussie, false sinon.
     */
    public boolean update(TypeSpecialite typeSpecialite) {
        if (typeSpecialite == null) {
            throw new IllegalArgumentException("Le TypeSpecialite à mettre à jour ne peut pas être null.");
        }
        if (typeSpecialite.getTsTypeId() <= 0) {
            throw new IllegalArgumentException("L'ID du TypeSpecialite doit être valide (supérieur à 0).");
        }

        String sql = "UPDATE typespecialiste SET tsTypeNom = ? WHERE tsTypeId = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, typeSpecialite.getTsTypeNom());
            statement.setInt(2, typeSpecialite.getTsTypeId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0; // Retourne true si la mise à jour a réussi
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du TypeSpecialite : " + typeSpecialite);
            e.printStackTrace();
        }
        return false; // Défensif : retourne false en cas d'échec
    }
}
