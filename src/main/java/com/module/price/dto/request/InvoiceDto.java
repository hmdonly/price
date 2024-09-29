package com.module.price.dto.request;

import com.module.price.enums.Currency;
import lombok.Data;

import java.util.List;

@Data
public class InvoiceDto {

    private List<ItemDto> items;
    private UserDto userDto;
    private double totalAmount;
    private Currency originalCurrency;
    private Currency targetCurrency;
}
