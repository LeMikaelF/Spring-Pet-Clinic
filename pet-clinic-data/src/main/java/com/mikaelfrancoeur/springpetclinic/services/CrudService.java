package com.mikaelfrancoeur.springpetclinic.services;

import com.mikaelfrancoeur.springpetclinic.model.Owner;

import java.util.Set;

public interface CrudService<T, ID> {
    T findById(ID id);

    Set<Owner> findAll();

    T save(T object);

    void delete(T object);

    void deleteById(T object);
}
