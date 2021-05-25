package com.builder.customer.port;

import com.builder.customer.domain.Customer;
import com.builder.customer.port.exception.CustomerException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> getCustomerById(UUID id);
    Optional<Customer> getCustomerByDocument(String document);
    List<Customer> getCustomersByName(String name, int pageNumber, int pageSize);
    List<Customer> getAll(int pageNumber, int pageSize);
    void save(Customer customer) throws CustomerException;
    void delete(UUID id);
}
