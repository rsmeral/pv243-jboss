package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.data.MoneyTransferListProducer;
import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.model.ExpenseReport;

import javax.annotation.ManagedBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@SessionScoped
@Named
//@Stateful
public class ExpenseController implements Serializable {
    private static long serialVersionUID = 1234243242343424L; //not generated value!!

    @Inject
    private PersonListProducer plp;

    @Inject
    private MoneyTransferListProducer mtlp;

    @Inject
    private ExpenseReportListProducer erlp;

    @Inject
    private ExpenseModel expenseModel;


    public String showSingleReport(Long id) {
        System.out.println("From reports to show details of report id=" + id);

        expenseModel.setExpenseReport(id);


//        try {
//            FacesContext.getCurrentInstance().getExternalContext()
//                    .redirect("report.xhtml");
//        } catch (IOException ex) {
//            System.out.println("error while showing single report" + ex);
//        }

        return "showSingleReport";
    }

}
