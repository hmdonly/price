package com.module.price.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    CURRENCY_EXCHANGE_ERROR("001", "Failed to get exchange rate"),
    UNEXPECTED_ERROR("002", "Unexpected error, please try again later");

    private final String code;
    private final String message;

    @Override
    public String toString() {
        return String.format("Error Code: %s, Message: %s", code, message);
    }
}
