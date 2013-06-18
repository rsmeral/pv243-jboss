package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.MoneyTransfer;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
public class MoneyTransferModel implements Serializable {

    private MoneyTransfer moneyTransfer;

    @Produces
    @Named("moneyTransfer")
    public MoneyTransfer getMoneyTransfer() {
        return moneyTransfer;
    }

    public void setMoneyTransfer(MoneyTransfer moneyTransfer) {
        this.moneyTransfer = moneyTransfer;
    }
}
