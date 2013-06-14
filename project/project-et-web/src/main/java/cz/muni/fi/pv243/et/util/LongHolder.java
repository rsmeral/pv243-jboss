package cz.muni.fi.pv243.et.util;

import java.io.Serializable;

public class LongHolder implements Serializable {

    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
