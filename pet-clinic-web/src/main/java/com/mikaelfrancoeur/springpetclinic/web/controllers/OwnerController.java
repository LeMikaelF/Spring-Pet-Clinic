package com.mikaelfrancoeur.springpetclinic.web.controllers;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String owners(Model model) {
        final Set<Owner> owners = ownerService.findAll();
        model.addAttribute("owners", owners);
        return "owners/index";
    }

    @RequestMapping({"/find"})
    public String findOwner() {
        //TODO stub
        return "notimplemented";
    }

    @GetMapping({"/{ownerId}", "/{ownerId}/show"})
    public String showOwner(@PathVariable Long ownerId, Model model) {
        final Owner owner = ownerService.findById(ownerId);
        model.addAttribute("owner", owner);
        return "owners/ownerDetails";
    }
}

