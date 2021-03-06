package com.mikaelfrancoeur.springpetclinic.services.map;

import com.mikaelfrancoeur.springpetclinic.model.Visit;
import com.mikaelfrancoeur.springpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile({"map", "default"})
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService {
    private AtomicLong counter = new AtomicLong();

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Visit save(Visit visit) {
        if (visit.getId() == null) {
            visit.setId(counter.getAndIncrement());
        }
        String errormsg = "Invalid visit";
        Objects.requireNonNull(visit.getPet(), errormsg);
        Objects.requireNonNull(visit.getPet().getOwner(), errormsg);
        Objects.requireNonNull(visit.getPet().getOwner().getId(), errormsg);
        return super.save(visit.getId(), visit);
    }

    @Override
    public void delete(Visit visit) {
        super.delete(visit.getId(), visit);
    }
}
