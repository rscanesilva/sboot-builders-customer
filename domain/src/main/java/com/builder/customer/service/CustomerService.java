package com.builder.customer.service;

import com.builder.customer.domain.Customer;
import com.builder.customer.port.CustomerRepository;
import com.builder.customer.port.exception.CustomerException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class CustomerService {

    CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return repository.getCustomerById(id);
    }

    public Optional<Customer> getCustomerByDocument(String document) {
        return repository.getCustomerByDocument(document);
    }

    public List<Customer> getCustomersByName(String name, int pageNumber, int pageSize) {
        return repository.getCustomersByName(name, pageNumber, pageSize);
    }

    public List<Customer> getAll(int pageNumber, int pageSize) {
        return repository.getAll(pageNumber, pageSize);
    }

    public void save(Customer customer) throws CustomerException {
        repository.save(customer);
    }

    public void delete(UUID id) {
        repository.delete(id);
    }
}
