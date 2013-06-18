package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.Payment;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
public class PaymentModel implements Serializable {

    private Payment payment;

    @Produces
    @Named
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
