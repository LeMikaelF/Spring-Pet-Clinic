package com.mikaelfrancoeur.springpetclinic.services;

import com.mikaelfrancoeur.springpetclinic.model.Vet;

import java.util.Set;

public interface VetService {

    Vet findById(Long id);

    Set<Vet> findAll();

    Vet save(Vet vet);
}
