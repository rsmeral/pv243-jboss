package cz.muni.fi.pv243.et.persistence;

import org.hibernate.Session;

import javax.enterprise.inject.Disposes;
import javax.persistence.EntityManager;

public abstract class AbstractEntityManagerProvider {

    public abstract EntityManager getEntityManager();
    public abstract Session getSession();

    public void closeEntityManager(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
