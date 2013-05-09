package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Transaction;

public interface TransactionRepository {

    public void createTransaction(Transaction transaction);

    public void updateTransaction(Transaction transaction);

    public void removeTransaction(Transaction transaction);
}
