package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.PersonWrapper;
import cz.muni.fi.pv243.et.service.ExpenseReportService;
import cz.muni.fi.pv243.et.service.MoneyTransferService;
import cz.muni.fi.pv243.et.util.CurrentPerson;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class MoneyTransferController {

    @Inject
    private MoneyTransferService service;

    @Inject
    private MoneyTransferModel model;

    @Inject
    private ExpenseReportService reportService;

    @Inject
    private ExpenseModel expenseModel;

    @Inject
    @CurrentPerson
    private PersonWrapper currentPersonWrapper;

    public String editMoneyTransfer(Long id) {
        MoneyTransfer mt = service.get(id);

        model.setMoneyTransfer(mt);

        return "/secured/editMoneyTransfer";
    }

    public String createMoneyTransfer() {
        MoneyTransfer mt = new MoneyTransfer();
        mt.setReport(expenseModel.getReport());
        model.setMoneyTransfer(mt);

        return "/secured/createMoneyTransfer";
    }

    public String saveMoneyTransfer() {
        MoneyTransfer mt = model.getMoneyTransfer();

        if (mt.getId() == null) {
            mt.setCreator(currentPersonWrapper.getPerson());
        }
        service.save(mt);
        // refresh current report
        refreshReport(mt);

        return "/secured/report?faces-redirect=true";
    }

    public String removeMoneyTransfer(MoneyTransfer mt) {
        model.setMoneyTransfer(mt);

        service.remove(mt);
        //
        refreshReport(mt);

        return "/secured/report?faces-redirect=true";
    }

    private void refreshReport(MoneyTransfer mt) {
        expenseModel.setReport(reportService.get(mt.getReport().getId()));
    }
}
