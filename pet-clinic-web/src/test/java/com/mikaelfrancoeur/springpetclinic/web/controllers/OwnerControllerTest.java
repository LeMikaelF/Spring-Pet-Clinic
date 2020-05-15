package com.mikaelfrancoeur.springpetclinic.web.controllers;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    final Long ownerId = 123L;
    final String ownerLastName = "lastName";
    @Mock
    Model model;
    @Mock
    OwnerService ownerService;
    @InjectMocks
    OwnerController ownerController;
    MockMvc mockMvc;
    String basePath = "/owners";

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    //Old functionality of owners root
    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"", "/", "/index", "/index.html"})
    @DisplayName("owners() uses correct template and returns owners as attributes.")
    void owners(String path) throws Exception {
        Set<Owner> owners = new HashSet<>();
        owners.add(new Owner());
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get(basePath + path)).andExpect(status().isOk()).andExpect(view().name("owners/index")).andExpect(model().attribute("owners", hasSize(1)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/owners/{ownerId}/show", "/owners/{ownerId}"})
    @DisplayName("showOwner() uses correct template and returns owner as attribute.")
    void showOwner() throws Exception {
        //given
        final Owner owner = new Owner();
        owner.setId(ownerId);
        when(ownerService.findById(eq(ownerId))).thenReturn(owner);

        //when
        mockMvc.perform(get("/owners/{ownerId}/show", ownerId))
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", owner))
                .andExpect(status().isOk());

        //then
        verify(ownerService).findById(eq(ownerId));
    }

    @Test
    @DisplayName("Displays the 'find owner' page without querying the owner service.")
    public void findOwner() throws Exception {
        mockMvc.perform((get("/owners/find")))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    @DisplayName("Shows the owner listing page when muliple owners are found.")
    public void showFoundOwnersMultiple() throws Exception {
        //given
        final Owner owner1 = new Owner();
        owner1.setId(ownerId);
        final Owner owner2 = new Owner();
        owner2.setId(ownerId + 1);
        final Set<Owner> owners = Stream.of(owner1, owner2).collect(Collectors.toSet());
        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(owners);

        //when-then
        mockMvc.perform(get("/owners")
                .param("lastName", ownerLastName))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));
    }

    @Test
    @DisplayName("Goes back to the find page and displays an error if no owner was found.")
    public void showFoundOwnersNone() throws Exception {
        //given
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new HashSet<>());

        //when
        mockMvc.perform(get("/owners")
                .param("lastName", ownerLastName))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));

        //then
        verify(ownerService).findAllByLastNameLike(anyString());
    }

    @Test
    @DisplayName("Goes straight to the owner details page if only one owner was found.")
    public void showFoundOwnersOne() throws Exception {
        //given
        final Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(ownerLastName);
        final Set<Owner> singleton = Collections.singleton(owner);
        when(ownerService.findAllByLastNameLike(eq(ownerLastName))).thenReturn(singleton);

        //when
        mockMvc.perform(get("/owners")
                .param("lastName", ownerLastName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%d/show", ownerId)))
                .andExpect(model().attribute("owner",
                        Matchers.hasProperty("lastName", equalTo(ownerLastName))));


        //then
        verify(ownerService).findAllByLastNameLike(eq(ownerLastName));
    }

    @Test
    @DisplayName("Lists all owners if last name is null.")
    public void showFoundOwnersAllOnEmptyLastName() throws Exception {
        //given
        final HashSet<Owner> set = new HashSet<>();
        set.add(new Owner());
        when(ownerService.findAll()).thenReturn(set);

        //when
        mockMvc.perform(get("/owners")
                .param("lastName", ""))
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attributeExists("owners"));

        //then
        verify(ownerService).findAll();
        verify(ownerService, never()).findAllByLastNameLike(any());
    }
}

