package cz.muni.fi.pv243.et.security;

import cz.muni.fi.pv243.et.controller.ExpenseModel;
import cz.muni.fi.pv243.et.model.ExpenseReport;
import cz.muni.fi.pv243.et.model.PersonWrapper;
import cz.muni.fi.pv243.et.service.ExpenseReportService;
import cz.muni.fi.pv243.et.util.CurrentPerson;
import org.picketlink.extensions.core.pbox.PicketBoxIdentity;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class IdentityHelper {

    @Inject
    private PicketBoxIdentity identity;

    @Inject
    @CurrentPerson
    private PersonWrapper personWrapper;

    @Inject
    private ExpenseReportService expenseReportService;

    public boolean hasAnyRole(String roles) {
        for (String role : roles.split(",")) {
            if (identity.getUserContext().hasRole(role.trim())) {
                return true;
            }
        }

        return false;
    }

    public boolean hasRole(String roles) {
        for (String role : roles.split(",")) {
            if (!identity.getUserContext().hasRole(role.trim())) {
                return false;
            }
        }

        return true;
    }

    public boolean isReportOwner(Long reportId) {
        ExpenseReport report = expenseReportService.get(reportId);
        return report != null && personWrapper.getPerson().equals(report.getSubmitter());
    }
}
