package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Transaction;
import javax.ejb.Local;

@Local
public interface TransactionRepository {

    public void createTransaction(Transaction transaction);

    public void updateTransaction(Transaction transaction);

    public void removeTransaction(Transaction transaction);
}
