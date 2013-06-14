package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.PurposeListProducer;
import cz.muni.fi.pv243.et.data.PurposeRepository;
import cz.muni.fi.pv243.et.model.Purpose;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Model
public class PurposeController {

    @Inject
    private PurposeListProducer purposeListProducer;

    @Inject
    private PurposeRepository purposeRepository;

    @Inject
    private PurposeModel purposeModel;

    @Produces
    @Named("purposes")
    public Collection<Purpose> getAllPurposes() {
        return purposeListProducer.getAll();
    }

    public String savePurpose() {
        if (purposeModel.getPurpose().getId() == null) {
            purposeRepository.create(purposeModel.getPurpose());
        } else {
            purposeRepository.update(purposeModel.getPurpose());
        }

        return "purposes?faces-redirect=true";
    }

    public String editPurpose(Long id) {
        Purpose p = purposeListProducer.get(id);
        purposeModel.setPurpose(p);

        return "editPurpose";
    }

    public String createPurpose() {
        purposeModel.setPurpose(new Purpose());

        return "createPurpose";
    }

    public String removePurpose(Long id) {
        purposeModel.setPurpose(null);

        Purpose toRemove = purposeListProducer.get(id);
        purposeRepository.remove(toRemove);

        return "purposes?faces-redirect=true";
    }
}
