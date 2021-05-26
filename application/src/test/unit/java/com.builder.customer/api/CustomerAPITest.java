package com.builder.customer.api;

import com.builder.customer.domain.Customer;
import com.builder.customer.domain.PersonType;
import com.builder.customer.mock.MocksCommon;
import com.builder.customer.port.exception.CustomerException;
import com.builder.customer.presentation.CustomerRepresentation;
import com.builder.customer.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("unit")
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerAPITest {

    @Mock CustomerService service;
    CustomerAPI api;
    private Customer customerPFMock;

    @Before
    public void config() {
        api = new CustomerAPI(service);
        customerPFMock = MocksCommon.getCustomerDomainMock(PersonType.PF);

        Mockito.when(service.getCustomerById(Mockito.any()))
                .thenReturn(Optional.of(customerPFMock));
        Mockito.when(service.getCustomerByDocument(Mockito.any()))
                .thenReturn(Optional.of(customerPFMock));
        Mockito.when(service.getCustomersByName(Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(customerPFMock));
        Mockito.when(service.getAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(customerPFMock));
    }

    @Test
    public void shouldReturnStatusCode409() throws CustomerException {
        final String MESSAGE = "Document already used";
        Mockito.doThrow(new CustomerException(MESSAGE, new Throwable()))
                .when(service)
                .save(Mockito.any(Customer.class));

        ResponseEntity responseEntity = api.addCustomer(MocksCommon.getCustomerRepresentationMock(PersonType.PF));
        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        Assert.assertEquals(MESSAGE, responseEntity.getBody());
    }

    @Test
    public void shouldReturnStatusCode400() throws CustomerException {
        final String MESSAGE = "Document invalid";
        Mockito.doThrow(new IllegalArgumentException(MESSAGE, new Throwable()))
                .when(service)
                .save(Mockito.any(Customer.class));

        ResponseEntity responseEntity = api.addCustomer(MocksCommon.getCustomerRepresentationMock(PersonType.PF));
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assert.assertEquals(MESSAGE, responseEntity.getBody());
    }

    @Test
    public void shouldCreateCustomerStatus201() throws CustomerException {
        Mockito.doNothing()
                .when(service)
                .save(Mockito.any(Customer.class));

        ResponseEntity responseEntity = api.addCustomer(MocksCommon.getCustomerRepresentationMock(PersonType.PF));
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void shouldUpdateCustomerStatus201() throws CustomerException {
        Mockito.doNothing()
                .when(service)
                .save(Mockito.any(Customer.class));

        ResponseEntity responseEntity = api.updateCustomer(MocksCommon.getCustomerRepresentationMock(PersonType.PF));
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void shouldDeleteCustomerStatus200() {
        Mockito.doNothing()
                .when(service)
                .delete(Mockito.any(UUID.class));
        ResponseEntity responseEntity = api.deleteCustomer(UUID.randomUUID());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnCustomersById() {
        ResponseEntity responseEntity = api.getCustomerById(MocksCommon.ID);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(MocksCommon.ID, ((CustomerRepresentation)responseEntity.getBody()).getId());
    }
    @Test
    public void shouldReturnEmptyCustomersById() {
        Mockito.when(service.getCustomerById(Mockito.any()))
                .thenReturn(Optional.empty());
        ResponseEntity responseEntity = api.getCustomerById(MocksCommon.ID);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assert.assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void shouldReturnCustomersByCPF() {
        ResponseEntity responseEntity = api.getCustomerByCPF(MocksCommon.CPF);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(MocksCommon.CPF, ((CustomerRepresentation)responseEntity.getBody()).getDocument());
    }

    @Test
    public void shouldReturnEmptyCustomersByCPF() {
        Mockito.when(service.getCustomerByDocument(Mockito.any()))
                .thenReturn(Optional.empty());

        ResponseEntity responseEntity = api.getCustomerByCPF(MocksCommon.CPF);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assert.assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void shouldReturnCustomersByCNPJ() {
        Mockito.when(service.getCustomerByDocument(Mockito.any()))
                .thenReturn(Optional.of(MocksCommon.getCustomerDomainMock(PersonType.PJ)));

        ResponseEntity responseEntity = api.getCustomerByCNPJ(MocksCommon.CNPJ);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(MocksCommon.CNPJ, ((CustomerRepresentation)responseEntity.getBody()).getDocument());
    }

    @Test
    public void shouldReturnEmptyCustomersByCNPJ() {
        Mockito.when(service.getCustomerByDocument(Mockito.any()))
                .thenReturn(Optional.empty());

        ResponseEntity responseEntity = api.getCustomerByCNPJ(MocksCommon.CNPJ);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assert.assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void shouldReturnCustomersByName() throws CustomerException {
        ResponseEntity responseEntity = api.getCustomersByName(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(MocksCommon.ID, ((List<CustomerRepresentation>)responseEntity.getBody()).get(0).getId());
    }

    @Test
    public void shouldReturnEmptyCustomersByName() throws CustomerException {
        Mockito.when(service.getCustomersByName(Mockito.any(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());

        ResponseEntity responseEntity = api.getCustomersByName(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assert.assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void shouldReturnCustomers() {
        ResponseEntity responseEntity = api.getAllCustomers(Mockito.anyInt(), Mockito.anyInt());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(MocksCommon.ID, ((List<CustomerRepresentation>)responseEntity.getBody()).get(0).getId());
    }

    @Test
    public void shouldReturnEmptyCustomers() {
        Mockito.when(service.getAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());

        ResponseEntity responseEntity = api.getAllCustomers(Mockito.anyInt(), Mockito.anyInt());
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Assert.assertEquals(null, responseEntity.getBody());
    }
}
