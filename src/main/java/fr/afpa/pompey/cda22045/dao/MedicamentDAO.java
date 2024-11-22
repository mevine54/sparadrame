package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Client;
import fr.afpa.pompey.cda22045.models.Medicament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static fr.afpa.pompey.cda22045.Singleton.getConnection;

public class MedicamentDAO extends DAO<Medicament> {

    @Override
    public Medicament create(Medicament obj) {
        String sql = "INSERT INTO medicament (medi_nom, medi_prix, medi_date_mise_en_service, medi_quantite, TypeMedicamentEnum) VALUES (?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        PreparedStatement statement = null;

        statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setString(1, obj.getMediNom());
        statement.setDouble(2, obj.getMediPrix());
        statement.setDate(3, java.sql.Date.valueOf(obj.getMediDateMiseEnService()));
        statement.setInt(4, obj.getMediQuantite());
        statement.setString(5, obj.getTypeMedicament().getTmTypeNom);
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean update(Medicament obj) {
        return false;
    }

    @Override
    public Medicament getById(int id) {
        return null;
    }

    @Override
    public List<Medicament> getAll() {
        return List.of();
    }
}
