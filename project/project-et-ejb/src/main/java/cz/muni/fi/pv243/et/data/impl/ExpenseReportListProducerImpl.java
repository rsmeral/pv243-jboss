package cz.muni.fi.pv243.et.data.impl;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.ReportStatus;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;

@Stateless
public class ExpenseReportListProducerImpl implements ExpenseReportListProducer, Serializable {

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
        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        QueryBuilder queryBuilder = ftem.getSearchFactory().buildQueryBuilder().forEntity(ExpenseReport.class).get();
        Query query = queryBuilder.keyword().onField("submitter.id").matching(submitter.getId()).createQuery();

        FullTextQuery fullTextQuery = ftem.createFullTextQuery(query, ExpenseReport.class);
        return fullTextQuery.getResultList();
//        return session.createQuery("SELECT report FROM ExpenseReport report WHERE report.submitter.id = :submitterId")
//                .setParameter("submitterId", submitter.getId()).list();
    }

    @Override
    public Collection<ExpenseReport> getAllForVerifier(Person verifier) {
        FullTextEntityManager ftem = Search.getFullTextEntityManager(em);
        QueryBuilder queryBuilder = ftem.getSearchFactory().buildQueryBuilder().forEntity(ExpenseReport.class).get();
        Query query = queryBuilder.keyword().onField("verifier.id").matching(verifier.getId()).createQuery();

        FullTextQuery fullTextQuery = ftem.createFullTextQuery(query, ExpenseReport.class);
        return fullTextQuery.getResultList();
    }

    @Override
    public Collection<ExpenseReport> getAllWithNoVerifierAssigned() {
        return session.createQuery("SELECT report FROM ExpenseReport report WHERE report.verifier IS NULL").list();
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
