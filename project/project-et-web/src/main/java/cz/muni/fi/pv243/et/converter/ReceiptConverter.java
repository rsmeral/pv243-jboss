package cz.muni.fi.pv243.et.converter;

import cz.muni.fi.pv243.et.model.Receipt;
import cz.muni.fi.pv243.et.service.ReceiptService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ReceiptConverter implements Converter {

    @Inject
    private ReceiptService receiptService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Receipt r = receiptService.get(Long.valueOf(value));
        return r;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Receipt r = (Receipt) value;
        return r.getId().toString();
    }
}
