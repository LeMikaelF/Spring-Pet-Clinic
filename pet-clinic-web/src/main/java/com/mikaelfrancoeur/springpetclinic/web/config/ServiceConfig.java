package com.mikaelfrancoeur.springpetclinic.web.config;

import com.mikaelfrancoeur.springpetclinic.services.OwnerService;
import com.mikaelfrancoeur.springpetclinic.services.PetService;
import com.mikaelfrancoeur.springpetclinic.services.VetService;
import com.mikaelfrancoeur.springpetclinic.services.map.OwnerServiceMap;
import com.mikaelfrancoeur.springpetclinic.services.map.PetServiceMap;
import com.mikaelfrancoeur.springpetclinic.services.map.VetServiceMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ServiceConfig {
    @Bean
    @Profile({"map", "default"})
    OwnerService ownerService() {
        return new OwnerServiceMap();
    }

    @Bean
    @Profile({"map", "default"})
    PetService petService() {
        return new PetServiceMap();
    }

    @Bean
    @Profile({"map", "default"})
    VetService vetService() {
        return new VetServiceMap();
    }
}
