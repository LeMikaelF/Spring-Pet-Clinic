package com.mikaelfrancoeur.springpetclinic.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("com.mikaelfrancoeur.springpetclinic")
})
public class SpringPetClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPetClinicApplication.class, args);
    }

}
