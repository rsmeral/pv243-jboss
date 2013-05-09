package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Transaction;
import org.joda.time.DateTime;
import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

@Local
public interface PaymentListProducer {

    public Payment getPayment(Long id);

    public Payment getPayment(Transaction transaction);

    public Collection<Payment> getAllPayments();

    public Collection<Payment> getAllPayments(Person person);

    public List<Payment> getPaymentsBetweenDates(DateTime fromDate, DateTime toDate);
}
