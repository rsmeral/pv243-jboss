package cz.muni.fi.pv243.et.converter;

import cz.muni.fi.pv243.et.model.Purpose;
import cz.muni.fi.pv243.et.service.PurposeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PurposeConverter implements Converter {

    @Inject
    private PurposeService purposeService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return purposeService.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Purpose p = (Purpose) value;
        return p.getId().toString();
    }
}
