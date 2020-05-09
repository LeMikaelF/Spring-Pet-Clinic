package com.mikaelfrancoeur.springpetclinic.services.jpa;

import com.mikaelfrancoeur.springpetclinic.model.Pet;
import com.mikaelfrancoeur.springpetclinic.repositories.PetRepository;
import com.mikaelfrancoeur.springpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"springdatajpa"})
public class PetServiceJpa implements PetService {
    private final PetRepository petService;

    public PetServiceJpa(PetRepository petService) {
        this.petService = petService;
    }

    @Override
    public Pet findById(Long id) {
        return petService.findById(id).orElse(null);
    }

    @Override
    public Set<Pet> findAll() {
        Set<Pet> set = new HashSet<>();
        petService.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Pet save(Pet pet) {
        return petService.save(pet);
    }

    @Override
    public void delete(Pet pet) {
        petService.delete(pet);
    }

    @Override
    public void deleteById(Long id) {
        petService.deleteById(id);
    }
}
