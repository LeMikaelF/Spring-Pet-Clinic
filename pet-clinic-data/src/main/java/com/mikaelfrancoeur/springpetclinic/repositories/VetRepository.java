package com.mikaelfrancoeur.springpetclinic.repositories;

import com.mikaelfrancoeur.springpetclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetRepository extends CrudRepository<Vet, Long> {
}
