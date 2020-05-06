package com.mikaelfrancoeur.springpetclinic.web.bootstrap;

import com.mikaelfrancoeur.springpetclinic.model.Owner;
import com.mikaelfrancoeur.springpetclinic.model.Vet;
import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import com.mikaelfrancoeur.springpetclinic.services.VetService;
import com.mikaelfrancoeur.springpetclinic.services.map.OwnerServiceMap;
import com.mikaelfrancoeur.springpetclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataInitializer() {
        ownerService = new OwnerServiceMap();
        vetService = new VetServiceMap();

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

        ownerService.save(owner1);
        ownerService.save(owner2);
        vetService.save(vet1);
        vetService.save(vet2);
        System.out.println("Saved owners and vets in DataInitializer");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
