package cz.muni.fi.pv243.et.util;

import cz.muni.fi.pv243.et.controller.ExpenseModel;
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

    private List<MoneyTransfer> moneyTransfers;
    private List<Payment> payments;
    private BigDecimal totalValue;
    @Inject
    private ExpenseModel expenseModel;

    public void updateCurrentValues() {
        this.moneyTransfers = expenseModel.getCurrentMoneyTransfers();
        this.payments = expenseModel.getCurrentPayments();
    }

    @Produces
    @Named("totalReportValue")
    public BigDecimal getTotalValue() {
        updateCurrentValues();
        if (moneyTransfers == null) {
            throw new NullPointerException("moneyTransfers is null!");
        }
        if (payments == null) {
            throw new NullPointerException("payments is null");
        }

        // PAYMENT = money from employee to company
        // MONEY_TRANSFER = money from company to employee
        BigDecimal moneyReceived = BigDecimal.ZERO; // from EMP perspective
        for (MoneyTransfer mt : moneyTransfers) {
            moneyReceived = mt.getValue().add(moneyReceived);
        }
        System.out.println("moneyReceived=" + moneyReceived);

        BigDecimal moneyPayed = BigDecimal.ZERO; // from EMP perspective
        for (Payment p : payments) {
            moneyPayed = p.getValue().add(moneyPayed);
        }
        System.out.println("moneyPayed=" + moneyPayed);

        return moneyReceived.subtract(moneyPayed);
    }



}
