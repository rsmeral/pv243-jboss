package cz.muni.fi.pv243.et.controller;


import org.apache.deltaspike.core.api.exclude.annotation.Exclude;

import javax.enterprise.inject.Produces;
import javax.faces.application.ProjectStage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.apache.deltaspike.core.api.projectstage.ProjectStage.Production;

@Exclude(exceptIfProjectStage = Production.class)
public class ProductionEntityManagerProvider {

    @PersistenceContext(unitName="et-pu")
    private EntityManager entityManager;

    @Produces
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
