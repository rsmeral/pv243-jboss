package cz.muni.fi.pv243.et.data;


import cz.muni.fi.pv243.et.model.MoneyTransfer;

public interface MoneyTransferRepository {

    public void createMoneyTransfer(MoneyTransfer moneyTransfer);

    public void updateMoneyTransfer(MoneyTransfer moneyTransfer);

    public void removeMoneyTransfer(MoneyTransfer moneyTransfer);
}
