package com.builder.customer.domain;

import java.util.Optional;

public interface Document {
    Boolean validateDocumentNumber(String document, Boolean isFormatted);
    String getNumberWithMask();
    String getNumberWithoutMask();

    default String removeMask(String numberWithMask) {
        Optional<String> documentWithMaskOpt = Optional.of(numberWithMask);
        String numberWithoutMask =
                documentWithMaskOpt.orElseThrow(IllegalArgumentException::new)
                        .replaceAll("\\D+", "")
                        .trim();

        if (numberWithoutMask.isEmpty()) {
            throw new IllegalArgumentException("Invalid document number");
        }

        return numberWithoutMask;
    };
}
