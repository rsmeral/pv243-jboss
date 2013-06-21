package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.ExpenseReportRepository;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import org.jboss.ejb3.annotation.Clustered;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Clustered
@Stateless
public class ExpenseReportRepositoryImpl implements ExpenseReportRepository {

    @Inject
    private EntityManager em;

    @Override
    public void create(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException("payment is null");
        }
        if (report.getId() != null) {
            throw new IllegalArgumentException("payment.id is not null");
        }
        em.persist(report);
    }

    @Override
    public void update(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException("payment is null");
        }
        if (report.getId() == null) {
            throw new IllegalArgumentException("payment.id is null - not persisted");
        }
        em.merge(report);
    }

    @Override
    public void remove(ExpenseReport report) {
        if (report == null) {
            throw new IllegalArgumentException("payment is null");
        }
        if (report.getId() == null) {
            throw new IllegalArgumentException("payment.id is null - not persisted");
        }
        em.remove(em.find(ExpenseReport.class, report.getId()));
    }
}
