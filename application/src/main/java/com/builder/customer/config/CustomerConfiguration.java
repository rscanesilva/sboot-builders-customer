package com.builder.customer.config;

import com.builder.customer.infraestructure.CustomerRepositoryImpl;
import com.builder.customer.port.CustomerRepository;
import com.builder.customer.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfiguration {

    @Bean
    public CustomerRepository customerRepository(CustomerRepositoryImpl repository){
        return repository;
    }

    @Bean
    public CustomerService customerService (CustomerRepositoryImpl repository) {
        return new CustomerService(repository);
    }
}
