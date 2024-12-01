package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.models.Medicament;
import fr.afpa.pompey.cda22045.models.Medecin;
import fr.afpa.pompey.cda22045.models.Ordonnance;
import fr.afpa.pompey.cda22045.models.Specialiste;
import fr.afpa.pompey.cda22045.utilities.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceDAO extends DAO<Ordonnance> {

    @Override
    public Ordonnance create(Ordonnance obj) {
        if (obj == null) {
            throw new IllegalArgumentException("L'ordonnance ne peut pas être null.");
        }

        String sqlOrdonnance = "INSERT INTO ordonnance (ord_date, med_id, cli_id, specialiste_id) VALUES (?, ?, ?, ?)";
        String sqlMedicament = "INSERT INTO ordonnance_medicament (ord_id, medi_id) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getInstanceDB()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statementOrdonnance = connection.prepareStatement(sqlOrdonnance, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementMedicament = connection.prepareStatement(sqlMedicament)) {

                // Insertion dans Ordonnance
                statementOrdonnance.setDate(1, Date.valueOf(obj.getDate()));
                statementOrdonnance.setInt(2, obj.getMedecin().getUtiId());
                statementOrdonnance.setInt(3, obj.getPatient().getCliId());
                statementOrdonnance.setInt(4, obj.getSpecialiste().getUtiId());
                statementOrdonnance.executeUpdate();

                // Récupération de l'ID généré pour l'ordonnance
                try (ResultSet generatedKeys = statementOrdonnance.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setOrdId(generatedKeys.getInt(1));
                    }
                }

                // Insertion des médicaments associés
                for (Medicament medicament : obj.getMedicaments()) {
                    statementMedicament.setInt(1, obj.getOrdId());
                    statementMedicament.setInt(2, medicament.getMediId());
                    statementMedicament.addBatch();
                }
                statementMedicament.executeBatch();

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

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM ordonnance WHERE ord_id = ?";
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
    public boolean update(Ordonnance obj) {
        if (obj == null) {
            throw new IllegalArgumentException("L'ordonnance ne peut pas être null.");
        }

        String sql = "UPDATE ordonnance SET ord_date = ?, med_id = ?, cli_id = ?, specialiste_id = ? WHERE ord_id = ?";
        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(obj.getDate()));
            statement.setInt(2, obj.getMedecin().getUtiId());
            statement.setInt(3, obj.getPatient().getCliId());
            statement.setInt(4, obj.getSpecialiste().getUtiId());
            statement.setInt(5, obj.getOrdId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Ordonnance getById(int id) {
        String sql = "SELECT o.*, m.medi_id, m.medi_nom, m.medi_prix, m.medi_date_mise_service, m.medi_quantite, m.type_med_id " +
                "FROM ordonnance o " +
                "LEFT JOIN ordonnance_medicament om ON o.ord_id = om.ord_id " +
                "LEFT JOIN medicament m ON om.medi_id = m.medi_id " +
                "WHERE o.ord_id = ?";

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Ordonnance ordonnance = new Ordonnance(
                            resultSet.getInt("ord_id"),
                            resultSet.getDate("ord_date").toLocalDate(),
                            new MedecinDAO().getById(resultSet.getInt("med_id")),
                            new ClientDAO().getById(resultSet.getInt("cli_id")),
                            new ArrayList<>(), // Médicaments seront ajoutés
                            new SpecialisteDAO().getById(resultSet.getInt("specialiste_id"))
                    );

                    // Ajouter les médicaments à l'ordonnance
                    do {
                        Medicament medicament = new Medicament(
                                resultSet.getInt("medi_id"),
                                resultSet.getString("medi_nom"),
                                null, // TypeMedicament peut être ajouté si nécessaire
                                resultSet.getDouble("medi_prix"),
                                resultSet.getDate("medi_date_mise_service").toLocalDate(),
                                resultSet.getInt("medi_quantite")
                        );
                        ordonnance.getMedicaments().add(medicament);
                    } while (resultSet.next());

                    return ordonnance;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ordonnance> getAll() {
        String sql = "SELECT * FROM ordonnance";
        List<Ordonnance> ordonnances = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstanceDB();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ordonnances.add(getById(resultSet.getInt("ord_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordonnances;
    }
}
