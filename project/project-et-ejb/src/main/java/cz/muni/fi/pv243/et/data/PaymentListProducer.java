package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import javax.ejb.Local;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Local
public interface PaymentListProducer {

    Payment getPayment(Long id);

    Collection<Payment> getAllPayments();

    Collection<Payment> getAllPayments(Person person);

    List<Payment> getPaymentsBetweenDates(Date fromDate, Date toDate);

    List<Payment> get(ExpenseReport report);
}
