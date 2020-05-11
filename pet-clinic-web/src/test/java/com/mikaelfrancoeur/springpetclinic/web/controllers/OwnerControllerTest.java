package com.mikaelfrancoeur.springpetclinic.web.controllers;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    Model model;
    @Mock
    OwnerService ownerService;
    @InjectMocks
    OwnerController ownerController;
    Set<Owner> owners;

    MockMvc mockMvc;
    String basePath = "/owners";

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(new Owner());
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/", "/index", "/index.html"})
    @DisplayName("owners() uses correct template and returns owners as attributes.")
    void owners(String path) throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get(basePath + path)).andExpect(status().isOk()).andExpect(view().name("owners/index")).andExpect(model().attribute("owners", hasSize(1)));
    }

    @Test
    void findOwner() {
        //TODO not implemented yet
    }
}
