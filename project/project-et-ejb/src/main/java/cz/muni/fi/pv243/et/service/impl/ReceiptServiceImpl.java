package cz.muni.fi.pv243.et.service.impl;

import cz.muni.fi.pv243.et.data.ReceiptListProducer;
import cz.muni.fi.pv243.et.data.ReceiptRepository;
import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.PersonRole;
import cz.muni.fi.pv243.et.model.Receipt;
import cz.muni.fi.pv243.et.security.annotation.Authenticated;
import cz.muni.fi.pv243.et.security.annotation.Roles;
import cz.muni.fi.pv243.et.service.ReceiptService;
import org.jboss.ejb3.annotation.Clustered;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;

@Clustered
@Stateless
@Authenticated
public class ReceiptServiceImpl implements ReceiptService {

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptListProducer receiptListProducer;

    @Override
    public void save(Receipt receipt) {
        if (receipt == null) {
            throw new IllegalArgumentException();
        }
        if (receipt.getId() == null) {
            receiptRepository.create(receipt);
        } else {
            receiptRepository.update(receipt);
        }
    }

    @Override
    public void remove(Receipt receipt) {
        if (receipt == null) {
            throw new IllegalArgumentException();
        }

        receiptRepository.remove(receipt);
    }

    @Override
    public Receipt get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return receiptListProducer.getReceipt(id);
    }

    @Roles(value = {PersonRole.ADMIN, PersonRole.VERIFIER}, anyRole = true)
    @Override
    public Collection<Receipt> findAll() {
        return receiptListProducer.getAllReceipts();
    }

    @Override
    public Collection<Receipt> findForPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException();
        }

        return receiptListProducer.getReceipts(person);
    }

    @Override
    public Collection<Receipt> findFromDate(Date fromDate) {
        if (fromDate == null) {
            throw new IllegalArgumentException();
        }

        return receiptListProducer.getReceiptsFromDate(fromDate);
    }
}
