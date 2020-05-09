package com.mikaelfrancoeur.springpetclinic.services.jpa;

import com.mikaelfrancoeur.springpetclinic.model.Specialty;
import com.mikaelfrancoeur.springpetclinic.repositories.SpecialtyRepository;
import com.mikaelfrancoeur.springpetclinic.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"springdatajpa"})
public class SpecialtyServiceJpa implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceJpa(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Specialty findById(Long id) {
        return specialtyRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Specialty> findAll() {
        Set<Specialty> set = new HashSet<>();
        specialtyRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public void delete(Specialty specialty) {
        specialtyRepository.delete(specialty);
    }

    @Override
    public void deleteById(Long id) {
        specialtyRepository.deleteById(id);
    }
}
