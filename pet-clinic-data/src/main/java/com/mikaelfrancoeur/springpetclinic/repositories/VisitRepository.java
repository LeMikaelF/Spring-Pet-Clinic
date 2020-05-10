package com.mikaelfrancoeur.springpetclinic.repositories;

import com.mikaelfrancoeur.springpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {
}
