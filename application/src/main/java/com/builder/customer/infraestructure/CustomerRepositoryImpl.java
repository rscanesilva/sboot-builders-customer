package com.builder.customer.infraestructure;

import com.builder.customer.domain.Customer;
import com.builder.customer.port.exception.CustomerException;
import com.builder.customer.port.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private static final Logger log = LoggerFactory.getLogger(CustomerRepositoryImpl.class);
    private final SpringDataCustomerRepository repository;

    @Autowired
    public CustomerRepositoryImpl(SpringDataCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        Optional<CustomerEntity> customerEntity = repository.findById(id);

        if (customerEntity.isPresent()) {
            return Optional.of(customerEntity.get().toCustomer());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> getCustomerByDocument(String document) {
        CustomerEntity entity = repository.getCustomerByDocument(document);
        if (entity != null) {
            return Optional.of(entity.toCustomer());
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> getCustomersByName(String name, int pageNumber, int pageSize) {
        Page<CustomerEntity> pageCustomers =
                repository.getCustomersByName(name, PageRequest.of(pageNumber, pageSize));

        return pageCustomers
                .toList()
                .stream()
                .map(CustomerEntity::toCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getAll(int pageNumber, int pageSize) {
        Page<CustomerEntity> listCustomerEntity = repository.findAll(PageRequest.of(pageNumber, pageSize));
        return listCustomerEntity
                .stream()
                .map(CustomerEntity::toCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Customer customer) throws CustomerException {
        try {
            repository.save(CustomerEntity.fromCustomer(customer));
        }  catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage(), ex.getRootCause());
            throw new CustomerException("Document already used", ex);
        }
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
