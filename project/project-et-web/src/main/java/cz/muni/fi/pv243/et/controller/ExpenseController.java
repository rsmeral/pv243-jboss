package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.ExpenseReportListProducer;
import cz.muni.fi.pv243.et.data.MoneyTransferListProducer;
import cz.muni.fi.pv243.et.data.PaymentRepository;
import cz.muni.fi.pv243.et.data.PersonListProducer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.util.ReportComputing;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;

@Model
public class ExpenseController {
//    private static long serialVersionUID = 1234243242343424L; //not generated value!!

    @Inject
    private PersonListProducer plp;

    @Inject
    private MoneyTransferListProducer mtlp;

    @Inject
    private ExpenseReportListProducer erlp;

    @Inject
    private ExpenseModel expenseModel;

    @Inject
    private ReportComputing reportComputing;

    @Inject
    private PaymentRepository paymentRepository;

    public String showSingleReport(Long id) {
        System.out.println("From reports to show details of report id=" + id);

        expenseModel.setExpenseReport(id);
        reportComputing.getTotalValue();
        return "report";
    }

    public String editPayment(Long id) {
        System.out.println("Payment id=" + id);

        expenseModel.setPayment(id);

        return "editPayment";
    }

//    @Validation(Validation = )
    public String updatePayment() {
        paymentRepository.update(expenseModel.getPayment());
        return "report";
    }

    public String addPayment() {
        return "newPayment";
    }

    public String createPayment(Payment payment) {
        paymentRepository.create(payment);
        return "report";
    }





}
