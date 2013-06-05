package cz.muni.fi.pv243.et.persistence;

import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class IdentityManagementEntityManagerProvider {

    @PersistenceContext(unitName = "IdentityManagementPU")
    private EntityManager identityManager;

    @Produces
    @IdentityManager
    public EntityManager getIdentityManager() {
        return identityManager;
    }

    public void closeEntityManager(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
