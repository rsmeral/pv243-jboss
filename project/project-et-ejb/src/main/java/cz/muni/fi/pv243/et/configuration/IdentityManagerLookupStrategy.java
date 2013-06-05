package cz.muni.fi.pv243.et.configuration;

import cz.muni.fi.pv243.et.persistence.IdentityManagementEntityManager;
import org.picketbox.core.identity.jpa.EntityManagerLookupStrategy;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class IdentityManagerLookupStrategy extends EntityManagerLookupStrategy {

    @Inject
    @IdentityManagementEntityManager
    private Instance<EntityManager> entityManager;

    @Override
    protected EntityManager lookupEntityManager() {
        EntityManager entityManager = null;

        try {
            entityManager = this.entityManager.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entityManager;
    }
}
