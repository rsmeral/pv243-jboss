package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Person;
import cz.muni.fi.pv243.et.model.Receipt;
import javax.ejb.Local;

@Local
public interface ReceiptRepository {

    public void create(Receipt receipt);

    public void update(Receipt receipt);

    public void remove(Receipt receipt);
}
