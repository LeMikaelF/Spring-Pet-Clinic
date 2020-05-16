package com.mikaelfrancoeur.springpetclinic.web.controllers;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.model.Pet;
import com.mikaelfrancoeur.springpetclinic.model.PetType;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import com.mikaelfrancoeur.springpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {

    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetController(PetTypeService petTypeService, OwnerService ownerService) {
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @GetMapping("/pets/new")
    public String newPetPage(Model model, @ModelAttribute Owner owner) {
        final Pet pet = new Pet();
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/new")
    @Transactional
    public String newPetPost(@ModelAttribute Pet pet, @ModelAttribute Owner owner) {
        if (owner.getPets().stream().anyMatch(p -> Objects.equals(pet.getId(), p.getId()))) {
            throw new IllegalArgumentException("Pet id was already present in owner.");
        }
        owner.getPets().add(pet);
        ownerService.save(owner);
        return String.format("redirect:/owners/%d/show", owner.getId());
    }

    @GetMapping("/pets/{petId}/edit")
    public String editPetPage(Model model,
                              @ModelAttribute Owner owner,
                              @ModelAttribute Pet pet, BindingResult result,
                              @PathVariable Long petId) {
        final Optional<Pet> matchingPet = owner.getPets().stream().filter(p -> Objects.equals(p.getId(), petId)).findFirst();
        if (matchingPet.isPresent()) {
            final Pet foundPet = matchingPet.get();
            foundPet.setOwner(owner);
            model.addAttribute(foundPet);
            return "pets/createOrUpdatePetForm";
        }
        return null;
    }

    @PostMapping("/pets/{petId}/edit")
    @Transactional
    public String editPetPost(@ModelAttribute Owner owner,
                              @ModelAttribute Pet givenPet,
                              @PathVariable Long petId) {
        givenPet.setId(petId);
        owner.getPets().removeIf(p -> Objects.equals(givenPet.getId(), p.getId()));
        owner.getPets().add(givenPet);
        ownerService.save(owner);
        return String.format("redirect:/owners/%d/show", owner.getId());
    }

    @ModelAttribute("types")
    public Collection<String> populatePetTypes() {
        return petTypeService.findAll().stream().map(PetType::getName).collect(Collectors.toList());
    }

    @ModelAttribute("owner")
    public Owner populateOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder
    public void webDataBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }
}
