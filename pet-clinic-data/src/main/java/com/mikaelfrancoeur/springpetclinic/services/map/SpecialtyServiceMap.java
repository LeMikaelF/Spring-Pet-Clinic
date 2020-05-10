package com.mikaelfrancoeur.springpetclinic.services.map;

import com.mikaelfrancoeur.springpetclinic.model.Specialty;
import com.mikaelfrancoeur.springpetclinic.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile({"map", "default"})
public class SpecialtyServiceMap extends AbstractMapService<Specialty, Long> implements SpecialtyService {
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Specialty findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Specialty> findAll() {
        return super.findAll();
    }

    @Override
    public Specialty save(Specialty specialty) {
        if (specialty.getId() == null) {
            specialty.setId(counter.getAndIncrement());
        }
        return super.save(specialty.getId(), specialty);
    }

    @Override
    public void delete(Specialty specialty) {
        super.delete(specialty.getId(), specialty);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
