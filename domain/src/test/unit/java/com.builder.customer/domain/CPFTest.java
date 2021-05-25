package com.builder.customer;

import com.builder.customer.domain.CPF;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CPFTest {

    final String VALID_CPF_STRING_WITH_MASK = " 991.254.230-45 ";
    final String VALID_CPF_STRING_WITHOUT_MASK = " 99125423045 ";
    final String INVALID_CPF_STRING_WITH_MASK = " 399.553.398-11 ";
    final String INVALID_CPF_STRING_WITHOUT_MASK = " 39955339811 ";

    CPF cpf = new CPF(VALID_CPF_STRING_WITH_MASK);

    @Test
    public void shouldCreateCPFWithValidNumberWithoutMask(){
        assertEquals(VALID_CPF_STRING_WITHOUT_MASK.trim(), new CPF(VALID_CPF_STRING_WITH_MASK).getNumber());
        assertEquals(VALID_CPF_STRING_WITHOUT_MASK.trim(), new CPF(VALID_CPF_STRING_WITHOUT_MASK).getNumber());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenConstructorReceiveInvalidCPF() {

        IllegalArgumentException ex1 = assertThrows(
                IllegalArgumentException.class,
                () -> {  new CPF(INVALID_CPF_STRING_WITH_MASK); }
        );

        IllegalArgumentException ex2 = assertThrows(
                IllegalArgumentException.class,
                () -> {  new CPF("InvalidCPFFormat"); }
        );

        assertEquals("Invalid CPF", ex1.getMessage());
        assertEquals("Invalid document number", ex2.getMessage());
    }

    @Test
    public void shouldReturnTrueFromValidadeDocumentNumber(){
        assertEquals(Boolean.TRUE, cpf.validateDocumentNumber(VALID_CPF_STRING_WITH_MASK, true));
        assertEquals(Boolean.TRUE, cpf.validateDocumentNumber(VALID_CPF_STRING_WITHOUT_MASK, false));
    }

    @Test
    public void shouldReturnFalseFromValidadeDocumentNumber(){
        assertEquals(Boolean.FALSE, cpf.validateDocumentNumber(INVALID_CPF_STRING_WITH_MASK, true));
        assertEquals(Boolean.FALSE, cpf.validateDocumentNumber(INVALID_CPF_STRING_WITHOUT_MASK, false));
        assertEquals(Boolean.FALSE, cpf.validateDocumentNumber(" ", true));
        assertEquals(Boolean.FALSE, cpf.validateDocumentNumber(null, false));
    }

    @Test
    public void shouldReturnStringWithouSpecialCharacters(){
        assertEquals(VALID_CPF_STRING_WITHOUT_MASK.trim(), new CPF(VALID_CPF_STRING_WITH_MASK).getNumberWithoutMask());
        assertEquals(VALID_CPF_STRING_WITHOUT_MASK.trim(), new CPF(VALID_CPF_STRING_WITHOUT_MASK).getNumberWithoutMask());
    }

    @Test
    public void shouldReturnCPFWithMask() {
        assertEquals(VALID_CPF_STRING_WITH_MASK.trim(), new CPF(VALID_CPF_STRING_WITHOUT_MASK).getNumberWithMask());
    }
}
