package cz.muni.fi.pv243.et.data;


import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;

import java.util.List;

public interface PurposeListProducer {

    public Purpose getPurpose(Long id);

    public Purpose getPurpose(Payment payment);

    public List<Purpose> getAllPurposes();

}
