package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;
import javax.ejb.Local;
import java.util.Collection;

@Local
public interface PurposeListProducer {

    public Purpose get(Long id);

    public Purpose get(Payment payment);

    public Collection<Purpose> getAll();

}
