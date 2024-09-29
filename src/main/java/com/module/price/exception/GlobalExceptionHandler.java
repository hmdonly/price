package com.module.price.exception;


import com.module.price.dto.response.ErrorResponseDto;
import com.module.price.enums.ErrorMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceCalculationException.class)
    public ResponseEntity<ErrorResponseDto> handlePriceCalculationException(PriceCalculationException ex) {
        log.error("PriceCalculationException occurred: {}", ex.getError().toString());
        ErrorMessage error = ex.getError();
        ErrorResponseDto errorResponse = new ErrorResponseDto(error.getCode(), error.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        log.error("PriceCalculationException Generic Exception occurred: {}", ex.getMessage());
        ErrorMessage errorResponse = ErrorMessage.UNEXPECTED_ERROR;
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(errorResponse.getCode(), errorResponse.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

