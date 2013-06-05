package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

@Stateless
public class ExpenseModel {

    @Inject
    private ExpenseReportListProducer erlp;

    @Produces
    @Named("expenseReports")
    public Collection<ExpenseReport> getExpenseReports() {
        return erlp.getAll();
//    public List<ExpenseReport> getSubmitterExpenseReports(Person person) {
//        return em.createQuery("SELECT er FROM ExpenseReport er WHERE er.submitter = :personId").setParameter("personId", "1").getResultList();

//        return em.createQuery("select g from Game g where g.state=cz.muni.fi.j002.cardgamee.model.GameState.RUNNING", Game.class).getResultList();
    }
}
