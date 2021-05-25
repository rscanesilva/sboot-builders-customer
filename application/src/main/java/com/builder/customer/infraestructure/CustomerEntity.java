package com.builder.customer.infraestructure;

import com.builder.customer.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private String personType;
    @Column(unique = true)
    private String document;

    public Customer toCustomer() {
        PersonType personType = PersonType.valueOf(this.personType);
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

    public static CustomerEntity fromCustomer(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .birthDate(customer.getBirthDate())
                .personType(customer.getPersonType().getDescription())
                .document(customer.getDocument().getNumberWithoutMask())
                .build();
    }
}
