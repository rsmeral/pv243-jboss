package cz.muni.fi.pv243.et.persistence;


import org.hibernate.Session;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class HibernateSessionProducer {

    @Inject
    private EntityManager entityManager;

    @Produces
    public Session getEntityManagerSession() {
        return (Session) entityManager.getDelegate();
    }
}
