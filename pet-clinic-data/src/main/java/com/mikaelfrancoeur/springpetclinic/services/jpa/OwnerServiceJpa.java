package com.mikaelfrancoeur.springpetclinic.services.jpa;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.repositories.OwnerRepository;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"springdatajpa"})
public class OwnerServiceJpa implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceJpa(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName).orElse(null);
    }

    @Override
    public Set<Owner> findAllByLastNameLike(String lastName) {
        return ownerRepository.findAllByLastNameLike("%" + lastName + "%");
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Owner> findAll() {
        Set<Owner> set = new HashSet<>();
        ownerRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }
}
