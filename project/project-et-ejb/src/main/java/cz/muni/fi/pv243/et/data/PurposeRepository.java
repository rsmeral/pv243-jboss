package cz.muni.fi.pv243.et.data;

import cz.muni.fi.pv243.et.model.Purpose;
import javax.ejb.Local;


@Local
public interface PurposeRepository {

    void create(Purpose purpose);

    void update(Purpose purpose);

    void remove(Purpose purpose);
}
