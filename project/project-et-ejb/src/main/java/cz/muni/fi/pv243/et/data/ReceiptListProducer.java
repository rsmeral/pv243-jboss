package cz.muni.fi.pv243.et.data;


import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;
import javax.ejb.Local;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Local
public interface ReceiptListProducer {

    public Receipt getReceipt(Long id);

    public Collection<Receipt> getReceipts(Person importedBy);

    public List<Receipt> getReceiptsFromDate(Date fromDate);

    public Collection<Receipt> getAllReceipts();
}
