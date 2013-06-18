package cz.muni.fi.pv243.et.service;

import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;

import java.util.Collection;

public interface PurposeService {
    void save(Purpose purpose);
    void remove(Purpose purpose);
    Purpose get(Long id);
    Purpose getForPayment(Payment payment);
    Collection<Purpose> findAll();
}
