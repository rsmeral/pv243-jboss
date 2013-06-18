package cz.muni.fi.pv243.et.service;

import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;

import java.util.Collection;
import java.util.Date;

public interface ReceiptService {
    void save(Receipt receipt);
    void remove(Receipt receipt);
    Receipt get(Long id);
    Collection<Receipt> findAll();
    Collection<Receipt> findForPerson(Person person);
    Collection<Receipt> findFromDate(Date fromDate);
}
