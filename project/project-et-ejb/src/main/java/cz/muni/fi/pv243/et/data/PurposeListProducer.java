package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;
import javax.ejb.Local;
import java.util.Collection;

@Local
public interface PurposeListProducer {

    public Purpose getPurpose(Long id);

    public Purpose getPurpose(Payment payment);

    public Collection<Purpose> getAllPurposes();

}
