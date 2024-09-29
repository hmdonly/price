package com.module.price.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {

    ORIGINAL("INR"),
    TARGET("AED");
    private final String code;
}
