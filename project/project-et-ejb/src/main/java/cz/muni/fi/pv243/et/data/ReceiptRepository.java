package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Receipt;
import javax.ejb.Local;

@Local
public interface ReceiptRepository {

    void create(Receipt receipt);

    void update(Receipt receipt);

    void remove(Receipt receipt);
}
