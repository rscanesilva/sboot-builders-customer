package com.builder.customer;

import com.builder.customer.domain.CPF;
import com.builder.customer.domain.Customer;
import com.builder.customer.domain.PersonType;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class CustomerTest {

    @Test
    public void shouldReturn29years(){
        Customer customer = new Customer(
                null,
                "Rafael Scane",
                LocalDate.of(1991, 10, 19),
                PersonType.PF,
                new CPF("99125423045"));
        Long ageExpected = 29L;
        Assert.assertEquals(ageExpected, customer.getAge());
    }

    @Test( expected = IllegalArgumentException.class)
    public void shouldThrowExceptionBecauseConstructorNameIsNull(){
        Customer customer = new Customer(
                null,
                null,
                LocalDate.of(1991, 10, 19),
                PersonType.PF,
                new CPF("99125423045"));
    }

    @Test( expected = IllegalArgumentException.class)
    public void shouldThrowExceptionBecauseConstructorNameIsEmpty(){
        Customer customer = new Customer(
                null,
                "",
                LocalDate.of(1991, 10, 19),
                PersonType.PF,
                new CPF("99125423045"));
    }

    @Test( expected = IllegalArgumentException.class)
    public void shouldThrowExceptionBecauseConstructorBirthDateIsNull(){
        Customer customer = new Customer(
                null,
                "Rafael Scane",
                null,
                PersonType.PF,
                new CPF("99125423045"));
    }

    @Test( expected = IllegalArgumentException.class)
    public void shouldThrowExceptionBecauseConstructorBirthDateNotPast(){
        Customer customer = new Customer(
                null,
                "Rafael Scane",
                LocalDate.now(),
                PersonType.PF,
                new CPF("99125423045"));
    }

    @Test( expected = IllegalArgumentException.class)
    public void shouldThrowExceptionBecauseConstructorPersonTypeIsNull(){
        Customer customer = new Customer(
                null,
                "Rafael Scane",
                LocalDate.of(1991, 10, 19),
                null,
                new CPF("99125423045"));
    }

}
