package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.MoneyTransfer;
import javax.ejb.Local;

@Local
public interface MoneyTransferRepository {

    public void create(MoneyTransfer moneyTransfer);

    public void update(MoneyTransfer moneyTransfer);

    public void remove(MoneyTransfer moneyTransfer);
}
