package cz.muni.fi.pv243.et.data;


import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;

public interface ReceiptRepository {

    public void createReceipt(Receipt receipt);

    public void updateReceipt(Long id);

    public void removeReceipt(Long id);
}
