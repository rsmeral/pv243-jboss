package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Purpose;
import javax.ejb.Local;


@Local
public interface PurposeRepository {

    public void create(Purpose purpose);

    public void update(Purpose purpose);

    public void remove(Long id);
}
