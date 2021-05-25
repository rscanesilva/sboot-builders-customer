package com.builder.customer.mock;

import com.builder.customer.domain.*;
import com.builder.customer.infraestructure.CustomerEntity;
import com.builder.customer.presentation.CustomerRepresentation;

import java.time.LocalDate;
import java.util.UUID;

public class MocksCommon {

    public static final String NAME = "Rafael Scane";
    public static final LocalDate BIRTHDATE = LocalDate.of(1991, 10, 19);
    public static final String CPF = "568.108.380-23";
    public static final String CNPJ = "11.353.623/0001-64";
    public static final String PF = "PF";
    public static final UUID ID = UUID.randomUUID();


    public static Customer getCustomerDomainMock(PersonType personType){
        return new Customer(ID,
                NAME,
                BIRTHDATE,
                personType,
                getDocument(personType)
        );
    }

    public static CustomerEntity getCustomerEntityMock(PersonType personType){
        return CustomerEntity.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTHDATE)
                .document(getDocument(personType).getNumberWithoutMask())
                .personType(personType.getDescription())
                .build();

    }

    public static CustomerRepresentation getCustomerRepresentationMock(PersonType personType){
        return CustomerRepresentation.builder()
                .id(ID)
                .name(NAME)
                .birthDate(BIRTHDATE)
                .document(getDocument(personType).getNumberWithMask())
                .personType(personType.getDescription())
                .build();
    }

    private static Document getDocument(PersonType personType) {
        Document doc = null;
        if (personType.equals(PersonType.PF)) {
            doc = new CPF(CPF);
        } else {
            doc = new CNPJ(CNPJ);
        }
        return doc;
    }
}
