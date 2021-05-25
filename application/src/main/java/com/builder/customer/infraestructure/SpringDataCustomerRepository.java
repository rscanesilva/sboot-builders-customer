package com.builder.customer.infraestructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataCustomerRepository extends PagingAndSortingRepository<CustomerEntity, UUID> {

    @Query(value = "select customer from CustomerEntity customer where customer.document like %:document%")
    CustomerEntity getCustomerByDocument(@Param("document") String document);

    @Query(value = "select customer from CustomerEntity customer where customer.name like %:name%")
    Page<CustomerEntity> getCustomersByName(@Param("name") String name, Pageable pageable);

}
