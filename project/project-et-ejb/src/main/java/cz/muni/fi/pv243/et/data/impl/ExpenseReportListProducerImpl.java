package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.ReportStatus;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

@Stateless
public class ExpenseReportListProducerImpl implements ExpenseReportListProducer {

    @Inject
    private EntityManager em;

    @Inject
    private Session session;

    @Override
    public ExpenseReport get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return em.find(ExpenseReport.class, id);
    }

    public Collection<ExpenseReport> getAllForSubmitter(Person submitter) {
        return session.createQuery("SELECT report FROM ExpenseReport report WHERE report.submitter like :submitter")
                .setParameter("submitter", submitter).list();
    }


    @Override
    public Collection<ExpenseReport> getAllBy(ReportStatus status) {
        return session.createQuery("SELECT report FROM ExpenseReport report WHERE report.status = :status")
                .setParameter("status", status).list();
    }

    @Override
    public Collection<ExpenseReport> getAll() {
        return session.createQuery("SELECT report FROM ExpenseReport report").list();
    }


}
