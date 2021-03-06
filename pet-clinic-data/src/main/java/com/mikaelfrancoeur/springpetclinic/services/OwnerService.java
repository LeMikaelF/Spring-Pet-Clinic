package com.mikaelfrancoeur.springpetclinic.services;

import com.mikaelfrancoeur.springpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {
    //TODO remove method
    Owner findByLastName(String lastName);

    Set<Owner> findAllByLastNameLike(String lastName);
}
