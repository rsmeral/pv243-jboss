package cz.muni.fi.pv243.et.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.deltaspike.core.api.exclude.annotation.Exclude;
import org.apache.deltaspike.core.api.projectstage.ProjectStage.Production;
import org.hibernate.Session;
import org.jboss.ejb3.annotation.Clustered;
@Clustered
@Exclude(exceptIfProjectStage = Production.class)
@Stateless
public class ProductionEntityManagerProvider {

    @PersistenceContext(unitName = "et-pu")
    private EntityManager entityManager;

    @PersistenceContext(unitName = "et-pu")
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
