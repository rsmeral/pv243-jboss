package cz.muni.fi.pv243.et.converter;

import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.service.ExpenseReportService;
import org.jboss.solder.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExpenseReportConverter implements Converter {

    @Inject
    private ExpenseReportService service;

    @Inject
    private Logger log;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return service.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        ExpenseReport r = (ExpenseReport) value;
        return r.getId().toString();
    }
}
