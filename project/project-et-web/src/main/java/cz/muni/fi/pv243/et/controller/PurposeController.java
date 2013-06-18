package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.PurposeListProducer;
import cz.muni.fi.pv243.et.data.PurposeRepository;
import cz.muni.fi.pv243.et.model.Purpose;
import cz.muni.fi.pv243.et.service.PurposeService;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Model
public class PurposeController {

    @Inject
    private PurposeService purposeService;

    @Inject
    private PurposeModel purposeModel;

    @Produces
    @Named("purposes")
    public Collection<Purpose> getAllPurposes() {
        return purposeService.findAll();
    }

    public String savePurpose() {
        purposeService.save(purposeModel.getPurpose());

        return "/secured/purposes?faces-redirect=true";
    }

    public String editPurpose(Long id) {
        Purpose p = purposeService.get(id);
        purposeModel.setPurpose(p);

        return "/secured/editPurpose";
    }

    public String createPurpose() {
        purposeModel.setPurpose(new Purpose());

        return "/secured/createPurpose";
    }

    public String removePurpose(Long id) {
        purposeModel.setPurpose(null);

        Purpose toRemove = purposeService.get(id);
        purposeService.remove(toRemove);

        return "/secured/purposes?faces-redirect=true";
    }
}
