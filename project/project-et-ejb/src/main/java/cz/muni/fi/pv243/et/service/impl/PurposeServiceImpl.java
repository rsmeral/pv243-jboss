package cz.muni.fi.pv243.et.service.impl;

import cz.muni.fi.pv243.et.data.PurposeListProducer;
import cz.muni.fi.pv243.et.data.PurposeRepository;
import cz.muni.fi.pv243.et.model.Payment;
import cz.muni.fi.pv243.et.model.Purpose;
import cz.muni.fi.pv243.et.service.PurposeService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class PurposeServiceImpl implements PurposeService {

    @Inject
    private PurposeRepository purposeRepository;

    @Inject
    private PurposeListProducer purposeListProducer;

    @Override
    public void save(Purpose purpose) {
        if (purpose == null) {
            throw new IllegalArgumentException();
        }

        if (purpose.getId() == null) {
            purposeRepository.create(purpose);
        } else {
            purposeRepository.update(purpose);
        }
    }

    @Override
    public void remove(Purpose purpose) {
        if (purpose == null) {
            throw new IllegalArgumentException();
        }

        purposeRepository.remove(purpose);
    }

    @Override
    public Purpose get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return purposeListProducer.get(id);
    }

    @Override
    public Collection<Purpose> findAll() {
        return purposeListProducer.getAll();
    }

    @Override
    public Purpose getForPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException();
        }

        return purposeListProducer.getForPayment(payment);
    }
}
