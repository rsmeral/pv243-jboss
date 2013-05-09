package cz.muni.fi.pv243.et.data;


import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Person;

import java.util.List;

public interface PaymentRepository {

    public void createPayment(Payment payment);

    public void updatePayment(Payment payment);

    public boolean removePayment(Long id);


}
