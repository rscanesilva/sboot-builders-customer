package com.builder.customer.infraestructure;

import com.builder.customer.domain.Customer;
import com.builder.customer.domain.PersonType;
import com.builder.customer.mock.MocksCommon;
import org.junit.Assert;
import org.junit.Test;

public class CustomerEntityTest {

    @Test
    public void shouldReturnDomainFromRepresentation(){
        CustomerEntity entityMock = MocksCommon.getCustomerEntityMock(PersonType.PF);
        Customer domain = entityMock.toCustomer();
        Assert.assertEquals(entityMock.getId(), domain.getId());
        Assert.assertEquals(entityMock.getName(), domain.getName());
        Assert.assertEquals(entityMock.getBirthDate(), domain.getBirthDate());
        Assert.assertEquals(entityMock.getDocument(), domain.getDocument().getNumberWithoutMask());
        Assert.assertEquals(entityMock.getPersonType(), domain.getPersonType().getDescription());
    }

    @Test
    public void shouldReturnRepresentationFromDomain(){
        Customer domain = MocksCommon.getCustomerDomainMock(PersonType.PJ);
        CustomerEntity entity = CustomerEntity.fromCustomer(domain);
        Assert.assertEquals(domain.getId(), entity.getId());
        Assert.assertEquals(domain.getName(), entity.getName());
        Assert.assertEquals(domain.getBirthDate(), entity.getBirthDate());
        Assert.assertEquals(domain.getDocument().getNumberWithoutMask(), entity.getDocument());
        Assert.assertEquals(domain.getPersonType().getDescription(), entity.getPersonType());
    }


}
