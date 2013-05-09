package cz.muni.fi.pv243.et.data;


import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;

import java.util.List;

public interface ReceiptListProducer {

    public Receipt getReceipt(Long id);

    public List<Receipt> getReceipts(Person importedBy);

    public List<Receipt> getAllReceipts();
}
