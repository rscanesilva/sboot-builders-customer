package com.builder.customer.domain;

import br.com.caelum.stella.validation.CNPJValidator;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class CNPJ implements Document {
    private final Logger log = LoggerFactory.getLogger(Document.class);
    private String number;

    public CNPJ(String number) {
        this.setNumber(number);
    }

    @Override
    public Boolean validateDocumentNumber(String number, Boolean isFormatted) {
        try {
            new CNPJValidator(isFormatted).assertValid(number.trim());
        } catch (RuntimeException ex) {
            log.info("Invalid CNPJ");
            return false;
        }
        return true;
    }

    @Override
    public String getNumberWithMask() {
        return (number.substring(0, 2) + "." + number.substring(2, 5) + "." +
                number.substring(5, 8) + "/" + number.substring(8, 12) + "-" +
                number.substring(12, 14));
    }

    @Override
    public String getNumberWithoutMask() {
        return this.removeMask(this.number);
    }


    public void setNumber(String number) {
        number = this.removeMask(number);
        if(validateDocumentNumber(number, false)) {
            this.number = number;
        } else {
            log.info("Invalid CNPJ");
            throw new IllegalArgumentException("Invalid CNPJ");
        }
    }

}
