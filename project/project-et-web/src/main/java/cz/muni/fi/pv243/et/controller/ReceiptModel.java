package cz.muni.fi.pv243.et.controller;

import cz.muni.fi.pv243.et.model.Receipt;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
public class ReceiptModel implements Serializable {

//    private Receipt receipt;

    private Long receiptId;

//    @Produces
//    @Named("receipt")
//    public Receipt getReceipt() {
//        return receipt;
//    }
//    public void setReceipt(Receipt receipt) {
//        this.receipt = receipt;
//    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

}
