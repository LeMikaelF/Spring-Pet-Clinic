package com.mikaelfrancoeur.springpetclinic.web.controllers;

import com.mikaelfrancoeur.springpetclinic.model.Pet;
import com.mikaelfrancoeur.springpetclinic.model.Visit;
import com.mikaelfrancoeur.springpetclinic.services.PetService;
import com.mikaelfrancoeur.springpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
@Controller
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @GetMapping("/new")
    public String newVisitPage(Model model,
                               @PathVariable Long ownerId,
                               @PathVariable Long petId,
                               @ModelAttribute Pet pet) {
        final Visit visit = new Visit();
        visit.setPet(pet);
        model.addAttribute("visit", visit);
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/new")
    public String newVisitPost(Model model,
                               @Valid Visit visit,
                               @PathVariable Long ownerId,
                               @PathVariable Long petId) {
        final Visit savedVisit = visitService.save(visit);
        model.addAttribute("pet", savedVisit.getPet());
        return String.format("redirect:/owners/%d/pets/%d", ownerId, petId);
    }

    @GetMapping("/{visitId}/edit")
    public String editVisitPage(Model model,
                                @PathVariable Long visitId,
                                @ModelAttribute Pet pet) {
        final Visit foundVisit = visitService.findById(visitId);
        model.addAttribute("pet", pet);
        model.addAttribute("visit", foundVisit);

        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/{visitId}/edit")
    @Transactional
    public String editVisitPost(Model model,
                                @PathVariable Long visitId,
                                @PathVariable Long petId,
                                Visit givenVisit) {
        givenVisit.setId(visitId);
        final Visit savedVisit = visitService.save(givenVisit);
        final Pet savedPet = savedVisit.getPet();

        model.addAttribute("pet", savedPet);
        return String.format("redirect:/owners/%d/pets/%d", savedPet.getOwner().getId(), savedPet.getId());
    }

    @ModelAttribute("pet")
    public Pet addPetModelAttribute(@PathVariable Long petId) {
        return petService.findById(petId);
    }

}
