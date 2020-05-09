package com.mikaelfrancoeur.springpetclinic.services.jpa;

import com.mikaelfrancoeur.springpetclinic.model.Vet;
import com.mikaelfrancoeur.springpetclinic.repositories.VetRepository;
import com.mikaelfrancoeur.springpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"springdatajpa"})
public class VetServiceJpa implements VetService {
    private final VetRepository vetRepository;

    public VetServiceJpa(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Vet findById(Long id) {
        return vetRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> set = new HashSet<>();
        vetRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Vet save(Vet vet) {
        return vetRepository.save(vet);
    }

    @Override
    public void delete(Vet vet) {
        vetRepository.delete(vet);
    }

    @Override
    public void deleteById(Long id) {
        vetRepository.deleteById(id);
    }
}
