package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;
import javax.ejb.Local;
import java.util.List;

@Local
public interface PaymentRepository {

    void create(Payment payment);

    void update(Payment payment);

    void remove(Payment payment);


}
