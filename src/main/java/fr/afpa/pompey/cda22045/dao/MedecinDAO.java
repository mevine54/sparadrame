package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Adresse;
import fr.afpa.pompey.cda22045.models.Medecin;

import java.util.List;

import static fr.afpa.pompey.cda22045.Singleton.getConnection;
public class MedecinDAO extends DAO<Medecin> {

    @Override
    public Medecin create(Medecin obj) {

//        code SQL-JAVA
        String sql = "INSERT INTO medecin (nom, prenom, adresse_id, telephone, email, numeroAgrement) VALUES (?, ?, ?, ?, ?, ?)";

        return obj;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean update(Medecin obj) {
        return false;
    }

    @Override
    public Medecin getById(int id) {
        return null;
    }

    @Override
    public List<Medecin> getAll() {
        return List.of();
    }
}

