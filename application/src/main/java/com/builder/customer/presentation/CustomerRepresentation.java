package com.builder.customer.presentation;

import com.builder.customer.api.CustomerAPI;
import com.builder.customer.domain.*;
import com.builder.customer.port.exception.CustomerException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRepresentation extends RepresentationModel<CustomerRepresentation>{

    @Id
    private UUID id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private String personType;
    @Column(unique = true)
    private String document;
    private Long age;

    public Customer toCustomer() {
        PersonType personType = PersonType.valueOf(this.personType.toUpperCase());
        Document doc = null;
        if (personType.equals(PersonType.PF)) {
            doc = new CPF(this.document);
        } else {
            doc = new CNPJ(this.document);
        }
        return new Customer(
                this.id,
                this.name,
                this.birthDate,
                personType,
                doc);
    }

    public static CustomerRepresentation fromCustomer(Customer customer) {
        CustomerRepresentation representation = CustomerRepresentation.builder()
                .id(customer.getId())
                .name(customer.getName())
                .birthDate(customer.getBirthDate())
                .personType(customer.getPersonType().getDescription())
                .document(customer.getDocument().getNumberWithMask())
                .age(customer.getAge())
                .build();

        representation.add(linkTo(methodOn(CustomerAPI.class).getCustomerById(representation.id)).withSelfRel());
        if (representation.personType.equals(PersonType.PF.getDescription())) {
            representation.add(linkTo(methodOn(CustomerAPI.class).getCustomerByCPF(customer.getDocument().getNumberWithoutMask())).withSelfRel());
        }
        else {
            representation.add(linkTo(methodOn(CustomerAPI.class).getCustomerByCNPJ(customer.getDocument().getNumberWithoutMask())).withSelfRel());
        }
        representation.add(linkTo(methodOn(CustomerAPI.class).addCustomer(representation)).withRel("create"));
        representation.add(linkTo(methodOn(CustomerAPI.class).updateCustomer(representation)).withRel("update"));
        representation.add(linkTo(methodOn(CustomerAPI.class).deleteCustomer(representation.id)).withRel("delete"));
        representation.add(linkTo(methodOn(CustomerAPI.class).getAllCustomers(0, 10)).withRel("customers"));
        representation.add(linkTo(methodOn(CustomerAPI.class).getCustomersByName(representation.name, 0, 10)).withRel("customersByName"));
        return representation;
    }
}
