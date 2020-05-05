package com.mikaelfrancoeur.springpetclinic.services;

import com.mikaelfrancoeur.springpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
