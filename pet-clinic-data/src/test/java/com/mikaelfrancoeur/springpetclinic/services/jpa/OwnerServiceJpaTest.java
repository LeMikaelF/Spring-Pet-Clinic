package com.mikaelfrancoeur.springpetclinic.services.jpa;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJpaTest {

    @Mock
    OwnerRepository repository;
    @InjectMocks
    OwnerServiceJpa service;
    final String ownerTel = "telephone";
    final Long ownerId = 12345L;
    final String ownerLastName = "lastName";
    Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(ownerLastName);
        owner.setTelephone(ownerTel);
    }

    @Test
    @DisplayName("Finds an existing owner from last name.")
    void findByLastNameExisting() {
        when(repository.findByLastName(eq(ownerLastName))).thenReturn(Optional.of(owner));
        assertEquals(owner, service.findByLastName("lastName"));
    }

    @Test
    @DisplayName("Returns empty optional when findByLastName() is called with non-existing string.")
    void findByLastNameNonExisting() {
//        when(repository.findByLastName(not(eq(ownerLastName)))).thenReturn(null);
        when(repository.findByLastName(not(eq(ownerLastName)))).thenReturn(Optional.empty());
        assertNull(service.findByLastName("abcdef"));
    }

    @Test
    @DisplayName("Finds an existing owner by id.")
    void findByIdExisting() {
        when(repository.findById(eq(ownerId))).thenReturn(Optional.of(owner));
        assertEquals(owner, service.findById(ownerId));
    }

    @Test
    @DisplayName("Returns null when findById() is called with a non-existing id")
    void findByIdNonExisting() {
        when(repository.findById(not(eq(ownerId)))).thenReturn(Optional.empty());
        assertNull(service.findById(99999L));
    }

    @Test
    @DisplayName("findall() returns the correct number of elements.")
    void findAll() {
        Set<Owner> set = new HashSet<>();
        set.add(owner);
        when(repository.findAll()).thenReturn(set);
        assertEquals(1, service.findAll().size());
    }

    @Test
    @DisplayName("Delegates saving to the repository.")
    void save() {
        service.save(owner);
        verify(repository).save(eq(owner));
    }

    @Test
    @DisplayName("Delegates deleting to the repository.")
    void delete() {
        service.delete(owner);
        verify(repository).delete(eq(owner));
    }

    @Test
    @DisplayName("Gives the correct id to the repository when deleting.")
    void deleteById() {
        service.deleteById(ownerId);
        verify(repository).deleteById(eq(ownerId));
    }
}
