package com.builder.customer.domain;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Getter
public class Customer {

    private UUID id;
    @NotBlank private String name;
    @NotNull @Past private LocalDate birthDate;
    @NotNull private PersonType personType;
    @NotNull private Document document;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Customer(UUID id, @NotBlank String name, LocalDate birthDate, PersonType personType, Document document) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.personType = personType;
        this.document = document;

        Set<ConstraintViolation<Customer>> violations = validator.validate(this);
        if(!violations.isEmpty()) {
            String message = "";
            for (ConstraintViolation<Customer> violation :violations) {
                message = String.format("%s %s\n", violation.getPropertyPath().toString(),violation.getMessage());
            }
            System.out.println(message);
            throw new IllegalArgumentException(message);
        }
    }

    public Long getAge() {
        return birthDate.until(LocalDate.now(), ChronoUnit.YEARS);
    }

}
