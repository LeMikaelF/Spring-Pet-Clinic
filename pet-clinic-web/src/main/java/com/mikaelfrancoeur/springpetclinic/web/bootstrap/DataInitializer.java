package com.mikaelfrancoeur.springpetclinic.web.bootstrap;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.model.Vet;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import com.mikaelfrancoeur.springpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataInitializer(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");

        Owner owner2 = new Owner();
        owner1.setId(2L);
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");

        Vet vet1 = new Vet();
        owner1.setId(1L);
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        Vet vet2 = new Vet();
        owner1.setId(2L);
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        this.ownerService.save(owner1);
        this.ownerService.save(owner2);
        this.vetService.save(vet1);
        this.vetService.save(vet2);
        System.out.println("Saved owners and vets in DataInitializer");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
