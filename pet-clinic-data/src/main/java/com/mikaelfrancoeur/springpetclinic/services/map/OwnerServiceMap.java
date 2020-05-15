package com.mikaelfrancoeur.springpetclinic.services.map;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import com.mikaelfrancoeur.springpetclinic.services.PetService;
import com.mikaelfrancoeur.springpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Profile({"map", "default"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {
    private final PetTypeService petTypeService;
    private final PetService petService;
    private AtomicLong idCounter = new AtomicLong();

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner save(Owner owner) {
        if (owner == null) {
            return null;
        }
        if (owner.getId() == null) {
            owner.setId(idCounter.getAndIncrement());
        }
        if (owner.getPets() != null) {
            owner.getPets().forEach(pet -> {
                if (pet.getPetType() == null) {
                    throw new IllegalStateException("Pet type must not be null in Pet object.");
                }
                pet.setPetType(petTypeService.save(pet.getPetType()));
                pet.setId(petService.save(pet).getId());
            });
        }
        return super.save(owner.getId(), owner);
    }

    @Override
    public void delete(Owner owner) {
        super.delete(owner.getId(), owner);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return super.findAll().stream().filter(owner -> owner.getLastName().equals(lastName)).findFirst().orElse(null);
    }

    @Override
    public Set<Owner> findAllByLastNameLike(String lastName) {
        return findAll().stream()
                .filter(owner -> Objects.equals(owner.getLastName(), lastName))
                .collect(Collectors.toSet());
    }
}
