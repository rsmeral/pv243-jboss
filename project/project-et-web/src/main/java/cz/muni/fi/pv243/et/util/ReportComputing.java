package cz.muni.fi.pv243.et.util;

import cz.muni.fi.pv243.et.controller.ExpenseModel;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.Payment;

import javax.ejb.Stateful;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;


@Stateful
@Named
public class ReportComputing {

    public BigDecimal getTotalValue(ExpenseReport report) {
        // PAYMENT = money from employee to company
        // MONEY_TRANSFER = money from company to employee
        BigDecimal moneyReceived = BigDecimal.ZERO; // from EMP perspective

        for (MoneyTransfer mt : report.getMoneyTransfers()) {
            moneyReceived = mt.getValue().add(moneyReceived);
        }

        BigDecimal moneyPaid = BigDecimal.ZERO; // from EMP perspective
        for (Payment p : report.getPayments()) {
            moneyPaid = p.getValue().add(moneyPaid);
        }

        return moneyReceived.subtract(moneyPaid);
    }



}
