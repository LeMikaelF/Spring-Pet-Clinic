package com.mikaelfrancoeur.springpetclinic.services.map;

import com.mikaelfrancoeur.springpetclinic.model.Pet;
import com.mikaelfrancoeur.springpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile({"map", "default"})
public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {

    private AtomicLong idCounter = new AtomicLong();

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(idCounter.getAndIncrement());
        }
        return super.save(pet.getId(), pet);
    }

    @Override
    public void delete(Pet pet) {
        super.delete(pet.getId(), pet);
    }
}
