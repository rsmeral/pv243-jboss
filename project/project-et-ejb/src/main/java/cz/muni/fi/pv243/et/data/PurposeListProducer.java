package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;
import java.util.Collection;

public interface PurposeListProducer {

    public Purpose get(Long id);

    public Purpose getForPayment(Payment payment);

    public Collection<Purpose> getAll();

}
