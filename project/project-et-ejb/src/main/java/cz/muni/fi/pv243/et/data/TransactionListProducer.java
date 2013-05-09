package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.MoneyTransfer;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Transaction;

import java.util.Collection;

public interface TransactionListProducer {

    public Transaction getTransaction(MoneyTransfer moneyTransfer);

    public Transaction getTransaction(Payment payment);

    public Collection<Transaction> getAllTransactions();
}
