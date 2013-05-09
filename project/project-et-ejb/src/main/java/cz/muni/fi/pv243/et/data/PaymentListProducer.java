package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Transaction;
import org.joda.time.DateTime;

import java.util.List;

public interface PaymentListProducer {

    public Payment getPayment(Long id);

    public Payment getPayment(Transaction transaction);

    public List<Payment> getAllPayments();

    public List<Payment> getAllPayments(Person person);

    public List<Payment> getPaymentsBetweenDates(DateTime fromDate, DateTime toDate);
}
