package com.builder.customer.domain;

import br.com.caelum.stella.validation.CPFValidator;
import lombok.Getter;

@Getter
public class CPF implements Document {

    private String number;

    public CPF(String number) {
        this.setNumber(number);
    }

    @Override
    public Boolean validateDocumentNumber(String number, Boolean isFormatted) {
        try {
            new CPFValidator(isFormatted).assertValid(number.trim());
        } catch (RuntimeException ex) {
            return false;
        }
        return true;
    }

    @Override
    public String getNumberWithMask() {
        String cpf = String.valueOf(this.number);
        return (cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" + cpf.substring(9, 11));
    }

    public String getNumberWithoutMask() {
        return this.removeMask(this.number);
    }

    public void setNumber(String number) {
        number = this.removeMask(number);

        if(validateDocumentNumber(number, false)) {
            this.number = number;
        } else {
            throw new IllegalArgumentException("Invalid CPF");
        }

    }

}
