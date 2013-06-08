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

    public Payment getPayment(Long id);

    public Collection<Payment> getAllPayments();

    public Collection<Payment> getAllPayments(Person person);

    public List<Payment> getPaymentsBetweenDates(Date fromDate, Date toDate);

    public List<Payment> get(ExpenseReport report);
}
