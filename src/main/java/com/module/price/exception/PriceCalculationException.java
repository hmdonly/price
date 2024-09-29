package com.module.price.exception;

import com.module.price.enums.ErrorMessage;
import lombok.Data;

@Data
public class PriceCalculationException extends RuntimeException {

    ErrorMessage error;

    public PriceCalculationException(ErrorMessage error) {
        super(error.getMessage()); // Call to superclass constructor
        this.error = error;
    }


}
