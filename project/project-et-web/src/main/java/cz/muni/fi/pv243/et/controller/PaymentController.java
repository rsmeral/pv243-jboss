package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.service.ExpenseReportService;
import cz.muni.fi.pv243.et.service.PaymentService;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class PaymentController {

    @Inject
    private PaymentService paymentService;

    @Inject
    private ExpenseReportService reportService;

    @Inject
    private ExpenseModel expenseModel;

    @Inject
    private PaymentModel model;

    public String createPayment() {
        Payment p = new Payment();
        p.setReport(expenseModel.getReport());
        model.setPayment(p);

        return "/secured/createPayment";
    }

    public String editPayment(Long id) {
        model.setPayment(paymentService.get(id));

        return "/secured/editPayment";
    }

    public String savePayment() {
        Payment p = model.getPayment();

        paymentService.save(p);
        // refresh current report
        refreshReport(p);

        return "/secured/report?faces-redirect=true";
    }

    public String removePayment(Payment p) {
        model.setPayment(null);

        paymentService.remove(p);
        // refresh current report
        refreshReport(p);

        return "/secured/report?faces-redirect=true";
    }

    private void refreshReport(Payment p) {
        expenseModel.setReport(reportService.get(p.getReport().getId()));
    }
}
