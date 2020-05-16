package com.mikaelfrancoeur.springpetclinic.web.controllers;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.model.Pet;
import com.mikaelfrancoeur.springpetclinic.model.PetType;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import com.mikaelfrancoeur.springpetclinic.services.PetService;
import com.mikaelfrancoeur.springpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PetControllerTest {
    final Long ownerId = 123L;
    final String ownerLastName = "lastName";
    final String petName = "-name of pet-";
    Owner owner;
    @Mock
    PetTypeService petTypeService;
    PetController petController;
    @Mock
    OwnerService ownerService;
    MockMvc mockMvc;
    Set<PetType> petTypes;
    @Mock
    PetService petService;
    private Long petId = 345L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        petController = new PetController(petTypeService, ownerService);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(ownerLastName);

        petTypes = new HashSet<>();
        final PetType petType1 = new PetType("petType1");
        final PetType petType2 = new PetType("petType1");
        petTypes = Stream.of(petType1, petType2).collect(Collectors.toSet());

    }

    @Test
    @DisplayName("@ModelAttribute returns correctly populated types.")
    void populatePetTypes() {
        //given
        when(petTypeService.findAll()).thenReturn(petTypes);

        //when
        final Collection<String> populatedPetTypes = petController.populatePetTypes();

        //then
        verify(petTypeService).findAll();
        assertEquals(petTypes.size(), populatedPetTypes.size());
    }

    @Test
    @DisplayName("@ModelAttribute returns correctly populated owner.")
    void populateOwner() {
        //given
        final Owner givenOwner = new Owner();
        givenOwner.setId(ownerId);
        when(ownerService.findById(ownerId)).thenReturn(givenOwner);

        //when
        final Owner populatedOwner = petController.populateOwner(ownerId);

        //then
        verify(ownerService).findById(ownerId);
        assertEquals(givenOwner, populatedOwner);
    }

    @Test
    @DisplayName("Shows pet creation page for new pet.")
    void newPetPage() throws Exception {
        //given
        when(ownerService.findById(ownerId)).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //when
        mockMvc.perform(get("/owners/{ownerId}/pets/new", ownerId))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("owner", "pet"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attribute("pet", hasProperty("owner", hasProperty("id", equalTo(ownerId)))));

        //then
        verify(ownerService).findById(ownerId);
    }

    @Test
    @DisplayName("Processes new pet POST")
    void newPetPost() throws Exception {
        //given
        when(ownerService.findById(ownerId)).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //when
        mockMvc.perform(post("/owners/{ownerId}/pets/new", ownerId)
                .param("name", petName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%d/show", ownerId)));

        //then
        verify(ownerService).findById(ownerId);
        verify(ownerService).save(MockitoHamcrest.argThat(hasProperty("pets", iterableWithSize(1))));
        verify(petTypeService).findAll();
    }

    @Test
    @DisplayName("Shows pet creation page for pet editing.")
    void editPetPage() throws Exception {
        //given
        final Pet pet = new Pet();
        pet.setId(petId);
        pet.setName(petName);
        owner.getPets().add(pet);
        when(ownerService.findById(ownerId)).thenReturn(owner);

        //when
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", ownerId, petId))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("owner", "pet", "types"))
                .andExpect(model().attribute("pet", hasProperty("owner", hasProperty("id", equalTo(ownerId)))));

        //then
        verify(ownerService).findById(ownerId);
    }

    @Test
    @DisplayName("Processes pet POST for existing pet.")
    void editPetPOST() throws Exception {
        //given
        when(ownerService.findById(ownerId)).thenReturn(owner);
        when(ownerService.save(owner)).thenReturn(owner);

        //when
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", ownerId, petId)
                .param("name", petName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%d/show", ownerId)));

        //then
        verify(ownerService).save(MockitoHamcrest.argThat(hasProperty("pets", hasSize(1))));
        verify(ownerService).findById(ownerId);
    }
}
