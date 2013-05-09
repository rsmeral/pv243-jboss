package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Purpose;
import javax.ejb.Local;


@Local
public interface PurposeRepository {

    public void createPurpose(Purpose purpose);

    public void updatePurpose(Purpose purpose);

    public void removePurpose(Purpose purpose);
}
