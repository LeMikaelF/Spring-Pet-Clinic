package com.mikaelfrancoeur.springpetclinic.repositories;

import com.mikaelfrancoeur.springpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
}
