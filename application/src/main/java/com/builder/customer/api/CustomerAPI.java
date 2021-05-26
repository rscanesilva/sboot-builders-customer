package com.builder.customer.api;

import com.builder.customer.domain.CNPJ;
import com.builder.customer.domain.CPF;
import com.builder.customer.presentation.CustomerRepresentation;
import com.builder.customer.domain.Customer;
import com.builder.customer.port.exception.CustomerException;
import com.builder.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class CustomerAPI {

    CustomerService service;

    @Autowired
    public CustomerAPI(CustomerService service) {
        this.service = service;
    }

    @PostMapping("customer")
    public ResponseEntity addCustomer(@RequestBody CustomerRepresentation representation) {
        representation.setId(null);
        return saveOrUpdate(representation);
    }

    @PutMapping("customer")
    public ResponseEntity updateCustomer(@RequestBody CustomerRepresentation representation) {
        return saveOrUpdate(representation);
    }

    @DeleteMapping(path = "customer/{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "customer/{id}")
    public ResponseEntity getCustomerById(@PathVariable("id") UUID id) {
        Optional<Customer> customer = service.getCustomerById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(CustomerRepresentation.fromCustomer(customer.get()));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "customer/pf")
    public ResponseEntity getCustomerByCPF(@RequestParam("cpf") String cpf) {
        try {
            Optional<Customer> customer = service.getCustomerByDocument(new CPF(cpf));
            if (customer.isPresent()) {
                return ResponseEntity.ok(CustomerRepresentation.fromCustomer(customer.get()));
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping(path = "customer/pj")
    public ResponseEntity getCustomerByCNPJ(@RequestParam("cnpj") String cnpj) {
        try {
            Optional<Customer> customer = service.getCustomerByDocument(new CNPJ(cnpj));
            if (customer.isPresent()) {
                return ResponseEntity.ok(CustomerRepresentation.fromCustomer(customer.get()));
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @GetMapping(path = "customers/{name}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<CustomerRepresentation>> getCustomersByName(
            @PathVariable(value = "name") String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){

        final List<Customer> listCustomers = service.getCustomersByName(name, page, pageSize);
        List<CustomerRepresentation> listCustomerRepresentation = listCustomers.stream()
                .map(CustomerRepresentation::fromCustomer)
                .collect(Collectors.toList());
        if (listCustomerRepresentation.size() > 0) {
            return ResponseEntity.ok(listCustomerRepresentation);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path="customers")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<CustomerRepresentation>> getAllCustomers(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize){

        final List<Customer> listCustomers = service.getAll(page, pageSize);
        List<CustomerRepresentation> listCustomerRepresentation = listCustomers.stream()
                .map(CustomerRepresentation::fromCustomer)
                .collect(Collectors.toList());

        if (listCustomerRepresentation.size() > 0) {
            return ResponseEntity.ok(listCustomerRepresentation);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ResponseEntity saveOrUpdate(@RequestBody CustomerRepresentation representation) {
        try{
            service.save(representation.toCustomer());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (CustomerException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
