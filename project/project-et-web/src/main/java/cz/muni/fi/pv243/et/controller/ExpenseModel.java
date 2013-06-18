package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.data.*;
import cz.muni.fi.pv243.et.model.*;
import cz.muni.fi.pv243.et.model.Currency;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;


/**
 * Service layer (?)
 */

@SessionScoped
public class ExpenseModel implements Serializable {

    private ExpenseReport report;

    @Produces
    @Named("expenseReport")
    public ExpenseReport getReport() {
        return report;
    }

    public void setReport(ExpenseReport report) {
        this.report = report;
    }
}
