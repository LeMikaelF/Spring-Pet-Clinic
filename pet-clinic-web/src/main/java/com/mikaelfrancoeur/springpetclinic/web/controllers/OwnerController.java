package com.mikaelfrancoeur.springpetclinic.web.controllers;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Set;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

/*
    @RequestMapping({"", "/", "/index", "/index.html"})
    public String owners(Model model) {
        final Set<Owner> owners = ownerService.findAll();
        model.addAttribute("owners", owners);
        return "owners/index";
    }
*/

    @GetMapping("")
    public String findOwners(Owner owner, Model model, BindingResult result) {
        if (owner.getLastName() == null || owner.getLastName().isEmpty()) {
            final Set<Owner> owners = ownerService.findAll();
            model.addAttribute("owners", owners);
            return "owners/ownersList";
        }
        final ArrayList<Owner> owners = new ArrayList<>(ownerService.findAllByLastNameLike(owner.getLastName()));
        if (owners.isEmpty()) {
            result.rejectValue("lastName", "notFound");
            return "owners/findOwners";
        } else if (owners.size() == 1) {
            model.addAttribute("owner", owners.iterator().next());
            return String.format("redirect:/owners/%d/show", owners.get(0).getId());
        } else {
            model.addAttribute("owners", owners);
            return "owners/ownersList";
        }


    }

    @RequestMapping({"/find"})
    public String findOwner(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping({"/{ownerId}", "/{ownerId}/show"})
    public String showOwner(@PathVariable Long ownerId, Model model) {
        final Owner owner = ownerService.findById(ownerId);
        model.addAttribute("owner", owner);
        return "owners/ownerDetails";
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }
}

