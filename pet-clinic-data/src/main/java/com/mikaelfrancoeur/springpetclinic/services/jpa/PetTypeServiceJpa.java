package com.mikaelfrancoeur.springpetclinic.services.jpa;

import com.mikaelfrancoeur.springpetclinic.model.PetType;
import com.mikaelfrancoeur.springpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"springdatajpa"})
public class PetTypeServiceJpa implements PetTypeService {
    private final PetTypeService petTypeService;

    public PetTypeServiceJpa(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType findById(Long id) {
        return petTypeService.findById(id);
    }

    @Override
    public Set<PetType> findAll() {
        return new HashSet<>(petTypeService.findAll());
    }

    @Override
    public PetType save(PetType petType) {
        return petTypeService.save(petType);
    }

    @Override
    public void delete(PetType petType) {
        petTypeService.delete(petType);
    }

    @Override
    public void deleteById(Long id) {
        petTypeService.deleteById(id);
    }
}
