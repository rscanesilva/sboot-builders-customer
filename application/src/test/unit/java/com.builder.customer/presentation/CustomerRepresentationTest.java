package com.builder.customer.presentation;

import com.builder.customer.domain.Customer;
import com.builder.customer.domain.PersonType;
import com.builder.customer.mock.MocksCommon;
import org.junit.Assert;
import org.junit.Test;

public class CustomerRepresentationTest {

    @Test
    public void shouldReturnDomainFromRepresentation(){
        CustomerRepresentation representation = MocksCommon.getCustomerRepresentationMock(PersonType.PF);
        Customer domain = representation.toCustomer();
        Assert.assertEquals(representation.getId(), domain.getId());
        Assert.assertEquals(representation.getName(), domain.getName());
        Assert.assertEquals(representation.getBirthDate(), domain.getBirthDate());
        Assert.assertEquals(representation.getDocument(), domain.getDocument().getNumberWithMask());
        Assert.assertEquals(representation.getPersonType(), domain.getPersonType().getDescription());
    }

    @Test
    public void shouldReturnRepresentationFromDomain(){
        Customer domain = MocksCommon.getCustomerDomainMock(PersonType.PF);
        CustomerRepresentation representation = CustomerRepresentation.fromCustomer(domain);
        Assert.assertEquals(domain.getId(), representation.getId());
        Assert.assertEquals(domain.getName(), representation.getName());
        Assert.assertEquals(domain.getBirthDate(), representation.getBirthDate());
        Assert.assertEquals(domain.getDocument().getNumberWithMask(), representation.getDocument());
        Assert.assertEquals(domain.getPersonType().getDescription(), representation.getPersonType());
    }

}
