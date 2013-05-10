package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import javax.ejb.Local;
import java.util.List;

@Local
public interface PaymentRepository {

    public void create(Payment payment);

    public void update(Payment payment);

    public void remove(Payment payment);


}
