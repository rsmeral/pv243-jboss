package cz.muni.fi.pv243.et.persistence;

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
    @IdentityManagementEntityManager
    public EntityManager getIdentityManager() {
        return identityManager;
    }

    public void closeEntityManager(@Disposes @IdentityManagementEntityManager EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
