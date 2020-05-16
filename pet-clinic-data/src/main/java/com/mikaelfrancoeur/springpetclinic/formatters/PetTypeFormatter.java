package com.mikaelfrancoeur.springpetclinic.formatters;

import com.mikaelfrancoeur.springpetclinic.model.PetType;
import com.mikaelfrancoeur.springpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

@Component
public class PetTypeFormatter implements Formatter<PetType> {
    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        return petTypeService.findAll().stream()
                .filter(petType -> Objects.equals(petType.getName(), text)).findFirst().orElseThrow(() -> new org.springframework.expression.ParseException(0, String.format("Could not parse string '%s' into a PetType.", text)));
    }

    @Override
    public String print(PetType object, Locale locale) {
        return object.getName();
    }
}
