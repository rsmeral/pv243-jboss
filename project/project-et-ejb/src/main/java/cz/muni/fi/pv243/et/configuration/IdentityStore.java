package cz.muni.fi.pv243.et.configuration;

import org.picketlink.idm.model.IdentityType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IdentityStore<E extends IdentityType> {
    private Map<String, E> map = new HashMap<String, E>();

    public void add(E type) {
        if (map.containsKey(type.getId())) {
            throw new IllegalArgumentException("key in the store!");
        }
        map.put(type.getId(), type);
    }

    public void update(E type) {
        if (!map.containsKey(type.getId())) {
            throw new IllegalArgumentException("key not in the store!");
        }
        map.put(type.getId(), type);
    }
    public void remove(E type) {
        if (!map.containsKey(type.getId())) {
            throw new IllegalArgumentException("key not in the store!");
        }
        map.remove(type.getId());
    }

    public E find(String key) {
        return map.get(key);
    }

    public Collection<E> findAll() {
        return map.values();
    }
}
