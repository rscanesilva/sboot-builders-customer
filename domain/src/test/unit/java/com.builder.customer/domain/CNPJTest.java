package com.builder.customer;

import com.builder.customer.domain.CNPJ;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CNPJTest {

    final String VALID_CNPJ_STRING_WITH_MASK = " 58.407.674/0001-95 ";
    final String VALID_CNPJ_STRING_WITHOUT_MASK = " 58407674000195 ";
    final String INVALID_CNPJ_STRING_WITH_MASK = " 58.407.674/0001-99 ";
    final String INVALID_CNPJ_STRING_WITHOUT_MASK = " 58407674000199 ";

    CNPJ cnpj = new CNPJ(VALID_CNPJ_STRING_WITH_MASK);

    @Test
    public void shouldCreateCNPJWithValidNumberWithoutMask(){
        assertEquals(VALID_CNPJ_STRING_WITHOUT_MASK.trim(), new CNPJ(VALID_CNPJ_STRING_WITH_MASK).getNumber());
        assertEquals(VALID_CNPJ_STRING_WITHOUT_MASK.trim(), new CNPJ(VALID_CNPJ_STRING_WITHOUT_MASK).getNumber());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenConstructorReceiveInvalidCPF() {

        IllegalArgumentException ex1 = assertThrows(
                IllegalArgumentException.class,
                () -> {  new CNPJ(INVALID_CNPJ_STRING_WITH_MASK); }
        );

        IllegalArgumentException ex2 = assertThrows(
                IllegalArgumentException.class,
                () -> {  new CNPJ("InvalidCNPJFormat"); }
        );

        assertEquals("Invalid CNPJ", ex1.getMessage());
        assertEquals("Invalid document number", ex2.getMessage());
    }

    @Test
    public void shouldReturnTrueFromValidateDocumentNumber(){
        assertEquals(Boolean.TRUE, cnpj.validateDocumentNumber(VALID_CNPJ_STRING_WITH_MASK, true));
        assertEquals(Boolean.TRUE, cnpj.validateDocumentNumber(VALID_CNPJ_STRING_WITHOUT_MASK,false));
    }

    @Test
    public void shouldReturnFalseFromValidateDocumentNumber(){
        assertEquals(Boolean.FALSE, cnpj.validateDocumentNumber(INVALID_CNPJ_STRING_WITH_MASK, true));
        assertEquals(Boolean.FALSE, cnpj.validateDocumentNumber(INVALID_CNPJ_STRING_WITHOUT_MASK, false));
        assertEquals(Boolean.FALSE, cnpj.validateDocumentNumber(" ", true));
        assertEquals(Boolean.FALSE, cnpj.validateDocumentNumber(null, false));
    }

    @Test
    public void shouldReturnStringWithouSpecialCharacters(){
        assertEquals(VALID_CNPJ_STRING_WITHOUT_MASK.trim(), new CNPJ(VALID_CNPJ_STRING_WITH_MASK).getNumberWithoutMask());
        assertEquals(VALID_CNPJ_STRING_WITHOUT_MASK.trim(), new CNPJ(VALID_CNPJ_STRING_WITHOUT_MASK).getNumberWithoutMask());
    }

    @Test
    public void shouldReturnCNPJWithMask() {
        assertEquals(VALID_CNPJ_STRING_WITH_MASK.trim(), new CNPJ(VALID_CNPJ_STRING_WITHOUT_MASK).getNumberWithMask());
    }
}
