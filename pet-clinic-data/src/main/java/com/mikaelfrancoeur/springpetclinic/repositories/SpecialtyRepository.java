package com.mikaelfrancoeur.springpetclinic.repositories;

import com.mikaelfrancoeur.springpetclinic.model.Specialty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends CrudRepository<Specialty, Long> {
}
