package fr.afpa.pompey.cda22045.dao;

import fr.afpa.pompey.cda22045.models.Achat;

import java.util.List;

public class AchatDAO extends DAO<Achat> {
    @Override
    public Achat create(Achat obj) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean update(Achat obj) {
        return false;
    }

    @Override
    public Achat getById(int id) {
        return null;
    }

    @Override
    public List<Achat> getAll() {
        return List.of();
    }
}
