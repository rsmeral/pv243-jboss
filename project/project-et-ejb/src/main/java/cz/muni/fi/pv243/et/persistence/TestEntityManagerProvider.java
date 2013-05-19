package cz.muni.fi.pv243.et.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.deltaspike.core.api.exclude.annotation.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage.Development;
import org.hibernate.Session;

@Exclude(exceptIfProjectStage = Development.class)
@Stateless
public class TestEntityManagerProvider extends AbstractEntityManagerProvider {

    @PersistenceContext(unitName = "TestPU")
    private EntityManager entityManager;

    @PersistenceContext(unitName = "TestPU")
    private Session session;
    
    @Produces
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Produces
    public Session getSession() {
        return session;
    }
}
