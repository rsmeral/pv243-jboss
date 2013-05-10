package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import javax.ejb.Local;
import java.util.List;

@Local
public interface PaymentRepository {

    public void createPayment(Payment payment);

    public void updatePayment(Payment payment);

    public void removePayment(Payment payment);


}
