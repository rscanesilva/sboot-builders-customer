package com.builder.customer.infraestructure;

import com.builder.customer.domain.Customer;
import com.builder.customer.domain.PersonType;
import com.builder.customer.mock.MocksCommon;
import com.builder.customer.port.exception.CustomerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("unit")
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerRepositoryImplTest {

    CustomerRepositoryImpl repositoryImpl;
    SpringDataCustomerRepository repository = Mockito.mock(SpringDataCustomerRepository.class);

    @Before
    public void config() {
        repositoryImpl = new CustomerRepositoryImpl(repository);

        CustomerEntity customerMock = MocksCommon.getCustomerEntityMock(PersonType.PF);
        List<CustomerEntity> listCustomersEntity = List.of(customerMock);
        Page<CustomerEntity> pageCustomers = new PageImpl<>(listCustomersEntity, PageRequest.of(0, 5), listCustomersEntity.size());

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.of(customerMock));

        Mockito.when(repository.getCustomerByDocument(Mockito.any()))
                .thenReturn(customerMock);

        Mockito.when(repository.getCustomersByName(Mockito.any(), Mockito.any(Pageable.class)))
                .thenReturn(pageCustomers);

        Mockito.when(repository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(pageCustomers);
    }

    @Test
    public void shouldReturnCustomerById(){
        Optional<Customer> customerOpt = repositoryImpl.getCustomerById(UUID.randomUUID());
        Customer customer = customerOpt.get();
        Assert.assertTrue(customerOpt.isPresent());
        validateCustomerReturned(customer);
    }

    @Test
    public void shouldReturnCustomerByDocument(){
        Optional<Customer> customerOpt = repositoryImpl.getCustomerByDocument("");
        Customer customer = customerOpt.get();
        Assert.assertTrue(customerOpt.isPresent());
        validateCustomerReturned(customer);
    }

    @Test
    public void shouldReturnCustomersByName(){
        List<Customer> listCustomers = repositoryImpl.getCustomersByName("", 0, 5);
        Customer customer = listCustomers.get(0);
        Assert.assertFalse(listCustomers.isEmpty());
        validateCustomerReturned(customer);
    }

    @Test
    public void shouldReturnAllCustomers(){
        List<Customer> listCustomers = repositoryImpl.getAll( 0, 5);
        Customer customer = listCustomers.get(0);
        Assert.assertFalse(listCustomers.isEmpty());
        validateCustomerReturned(customer);
    }

    @Test(expected = CustomerException.class)
    public void shouldThrowsCustomerException() throws CustomerException {
        Mockito.doThrow(new DataIntegrityViolationException(""))
                .when(repository)
                .save(Mockito.any());
        repositoryImpl.save(MocksCommon.getCustomerDomainMock(PersonType.PF));
    }



    private void validateCustomerReturned(Customer customer) {
        Assert.assertEquals(MocksCommon.NAME, customer.getName());
        Assert.assertEquals(MocksCommon.BIRTHDATE, customer.getBirthDate());
        Assert.assertEquals(MocksCommon.PF, customer.getPersonType().getDescription());
        Assert.assertEquals(MocksCommon.CPF, customer.getDocument().getNumberWithMask());
    }
}
