package com.builder.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonType {
    PF ("PF"),
    PJ ("PJ");
    private String description;
}
