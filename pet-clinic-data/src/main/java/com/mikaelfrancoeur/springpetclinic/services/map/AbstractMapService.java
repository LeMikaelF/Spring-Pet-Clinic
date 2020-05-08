package com.mikaelfrancoeur.springpetclinic.services.map;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractMapService<T, ID> {
    private ConcurrentHashMap<ID, T> map = new ConcurrentHashMap<>();

    T findById(ID id) {
        return map.get(id);
    }

    Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    T save(ID id, T object) {
        map.put(id, object);
        return object;
    }

    void delete(ID id, T object) {
        map.entrySet().removeIf(idtEntry -> idtEntry.getValue().equals(object));
    }

    void deleteById(ID id) {
        map.remove(id);
    }
}
