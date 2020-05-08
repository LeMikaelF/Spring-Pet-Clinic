package com.mikaelfrancoeur.springpetclinic.services.map;

import com.mikaelfrancoeur.springpetclinic.model.Vet;
import com.mikaelfrancoeur.springpetclinic.services.SpecialtyService;
import com.mikaelfrancoeur.springpetclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    private final AtomicLong idCounter = new AtomicLong();
    private final SpecialtyService specialtyService;

    public VetServiceMap(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Vet save(Vet vet) {
        if (vet.getId() == null) {
            vet.setId(idCounter.getAndIncrement());
        }
        vet.getSpecialties().forEach(specialty -> specialty.setId(specialtyService.save(specialty).getId()));

        return super.save(vet.getId(), vet);
    }

    @Override
    public void delete(Vet vet) {
        super.delete(vet.getId(), vet);
    }
}
