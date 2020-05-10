package com.mikaelfrancoeur.springpetclinic.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.mikaelfrancoeur.springpetclinic")
@EnableJpaRepositories(basePackages = {"com.mikaelfrancoeur.springpetclinic.repositories"})
@EntityScan(basePackages = {"com.mikaelfrancoeur.springpetclinic.model"})
public class SpringPetClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPetClinicApplication.class, args);
    }

}
