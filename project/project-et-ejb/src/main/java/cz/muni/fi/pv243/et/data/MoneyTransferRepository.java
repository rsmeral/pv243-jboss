package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.MoneyTransfer;
import javax.ejb.Local;

@Local
public interface MoneyTransferRepository {

    void create(MoneyTransfer moneyTransfer);

    void update(MoneyTransfer moneyTransfer);

    void remove(MoneyTransfer moneyTransfer);
}
