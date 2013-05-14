package cz.muni.fi.pv243.et.persistence;


import org.apache.deltaspike.core.api.exclude.annotation.Exclude;
import org.hibernate.Session;

import javax.enterprise.inject.Produces;
import javax.faces.application.ProjectStage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.apache.deltaspike.core.api.projectstage.ProjectStage.Development;

@Exclude(exceptIfProjectStage = Development.class)
public class TestEntityManagerProvider {

    @PersistenceContext(unitName="TestPU")
    private EntityManager entityManager;

    @Produces
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
