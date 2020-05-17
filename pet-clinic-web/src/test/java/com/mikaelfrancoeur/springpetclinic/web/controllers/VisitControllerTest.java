package com.mikaelfrancoeur.springpetclinic.web.controllers;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.model.Pet;
import com.mikaelfrancoeur.springpetclinic.model.Visit;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import com.mikaelfrancoeur.springpetclinic.services.PetService;
import com.mikaelfrancoeur.springpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VisitControllerTest {

    final Long ownerId = 123L;
    final Long petId = 345L;
    final LocalDate date = LocalDate.ofEpochDay(0);
    final Long visitId = 678L;
    @Mock
    OwnerService ownerService;
    VisitController visitController;
    @Mock
    VisitService visitService;
    MockMvc mockMvc;
    Pet pet;
    Owner owner;
    String visitDescription = "-visit description-";
    @Mock
    PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        visitController = new VisitController(visitService, petService);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();

        pet = new Pet();
        pet.setId(petId);

        owner = new Owner();
        owner.setId(ownerId);

        owner.getPets().add(pet);
        pet.setOwner(owner);
    }

    @Test
    void newVisitPage() throws Exception {
        //given
        when(petService.findById(petId)).thenReturn(pet);

        //when
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/new", ownerId, petId))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet", "visit"));

        //then
        verify(petService).findById(petId);
    }

    @Test
    void newVisitPost() throws Exception {
        //given
        final Visit visit = new Visit();
        visit.setDescription(visitDescription);
        visit.setPet(pet);
        visit.setDate(date);
        when(visitService.save(any())).thenReturn(visit);

        //when
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", ownerId, petId)
                .param("description", visitDescription)
                .param("date", "2018-11-11")
                .param("description", visitDescription))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%d/pets/%d", ownerId, petId)))
                .andExpect(model().attributeExists("pet"));

        //then
        verify(visitService).save(MockitoHamcrest.argThat(hasProperty("description")));
    }

    @Test
    void editVisitPage() throws Exception {
        //given
        final Visit visit = new Visit();
        visit.setDescription(visitDescription);
        visit.setPet(pet);
        visit.setDate(date);
        when(petService.findById(petId)).thenReturn(pet);
        when(visitService.findById(visitId)).thenReturn(visit);

        //when
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/{visitId}/edit", ownerId, petId, visitId))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet", "visit"));

        //then
        verify(petService).findById(petId);
        verify(visitService).findById(visitId);
    }

    @Test
    void editVisitPost() throws Exception {
        //given
        final Visit visit = new Visit();
        visit.setDescription(visitDescription);
        visit.setPet(pet);
        visit.setDate(date);
        when(visitService.save(any())).thenReturn(visit);
        when(petService.findById(petId)).thenReturn(pet);

        //when
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/visits/{visitId}/edit", ownerId, petId, visitId)
                .param("description", visitDescription))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%d/pets/%d", ownerId, petId)))
                .andExpect(model().attributeExists("pet"));

        //then
        verify(petService).findById(petId);
        verify(visitService).save(any());
    }

    @Test
    void addPetModelAttribute() {
        //given
        when(petService.findById(petId)).thenReturn(pet);

        //when
        final Pet foundPet = visitController.addPetModelAttribute(petId);

        //then
        assertEquals(pet, foundPet);
    }

}
