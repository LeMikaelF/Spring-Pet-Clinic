package com.mikaelfrancoeur.springpetclinic.services.map;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import com.mikaelfrancoeur.springpetclinic.services.PetService;
import com.mikaelfrancoeur.springpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    @Mock
    private PetService petService;
    @Mock
    private PetTypeService petTypeService;
    OwnerService ownerService;
    Long ownerId = 12345L;
    String ownerLastName = "LastName";
    Owner ownerInService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ownerService = new OwnerServiceMap(petTypeService, petService);
        ownerInService = new Owner();
        ownerInService.setId(ownerId);
        ownerInService.setLastName(ownerLastName);
        ownerService.save(ownerInService);
    }

    @Test
    @DisplayName("Finds an existing owner from id")
    void findById() {
        assertEquals(ownerId, ownerService.findById(ownerId).getId());
    }

    @Test
    @DisplayName("Returns null when calling findById() with a non-existing owner id")
    void findByIdNonExistent() {
        assertNull(ownerService.findById(99999L));
    }

    @Test
    @DisplayName("Returns the right number of elements.")
    void findAll() {
        final Set<Owner> ownerSet = ownerService.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    @DisplayName("Saves owner with null id and gives it an id")
    void saveNullId() {
        Owner owner = new Owner();
        final Owner saved = ownerService.save(owner);
        assertNotNull(saved.getId());
    }

    @Test
    @DisplayName("Saves owner with non-null id")
    void saveNonNullId() {
        final Owner owner = new Owner();
        owner.setId(ownerId);
        final Owner saved = ownerService.save(owner);
        assertEquals(ownerId, saved.getId());
    }

    @Test
    @DisplayName("Updates owner based on id equality")
    void updateOwner() {
        final Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setTelephone("12345");
        ownerService.save(owner);
        final Owner owner2 = new Owner();
        owner2.setId(ownerId);
        owner2.setTelephone("54321");
        final Owner savedOwner2 = ownerService.save(owner2);
        assertEquals(owner2, savedOwner2);
    }

    @Test
    @DisplayName("Deletes owner when it is in the repository")
    void deleteExisting() {
        ownerService.delete(ownerInService);
        assertEquals(0, ownerService.findAll().size());
    }

    @Test
    @DisplayName("Doesn't modify existing objects when deleting non-existing id")
    void deleteNonExisting() {
        final Owner owner = new Owner();
        ownerService.delete(owner);
        assertEquals(1, ownerService.findAll().size());
    }

    @Test
    @DisplayName("Deletes an owner with deleteById()")
    void deleteByIdExisting() {
        ownerService.deleteById(ownerInService.getId());
        assertEquals(0, ownerService.findAll().size());
    }

    @Test
    @DisplayName("Doesn't modify existing objects when deleteById() is called with non-existing id")
    void deleteByIdNonExisting() {
        ownerService.deleteById(9999L);
        assertEquals(1, ownerService.findAll().size());
    }

    @Test
    @DisplayName("Finds an existing owner with findByLastName()")
    void findByLastNameExisting() {
        assertEquals(ownerInService, ownerService.findByLastName(ownerLastName));
    }

    @Test
    @DisplayName("Returns null when findByLastName() is called with non-existing string")
    void findByLastNameNonExisting() {
        assertNull(ownerService.findByLastName("firstName"));
    }
}
