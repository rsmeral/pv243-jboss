package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.PurposeRepository;
import cz.muni.fi.pv243.et.model.Purpose;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class PurposeRepositoryImpl implements PurposeRepository {

    @Inject
    private EntityManager em;

    @Override
    public void create(Purpose purpose) {
        if (purpose == null) {
            throw new IllegalArgumentException("person is null");
        }

        if (purpose.getId() != null) {
            throw new IllegalArgumentException("person.id is null");
        }
        em.persist(purpose);
    }

    @Override
    public void update(Purpose purpose) {
        if (purpose == null) {
            throw new IllegalArgumentException("purpose is null");
        }

        if (purpose.getId() == null) {
            throw new IllegalArgumentException("purpose not persisted");
        }
        em.merge(purpose);
    }

    @Override
    public void remove(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("purpose is null");
        }

        if (id == null) {
            throw new IllegalArgumentException("not persisted entity");
        }
        em.remove(em.find(Purpose.class, id));
        //em.remove(purpose);
    }
}
