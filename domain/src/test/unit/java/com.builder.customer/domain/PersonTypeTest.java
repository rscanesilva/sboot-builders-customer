package com.builder.customer;

import com.builder.customer.domain.PersonType;
import org.junit.Assert;
import org.junit.Test;

public class PersonTypeTest {

    @Test
    public void shouldReturnPF() {
        Assert.assertEquals("PF", PersonType.PF.getDescription());
    }

    @Test
    public void shouldReturnPJ() {
        Assert.assertEquals("PJ", PersonType.PJ.getDescription());
    }
}
