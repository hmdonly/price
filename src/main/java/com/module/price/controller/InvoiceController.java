package com.module.price.controller;

import com.module.price.dto.request.InvoiceDto;
import com.module.price.service.impl.DiscountCalculationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class InvoiceController {


    DiscountCalculationServiceImpl discountCalculationServiceImpl;

    @PostMapping("/calculate")
    public double calculate(@RequestBody InvoiceDto invoiceDto) {
        return discountCalculationServiceImpl.calculatePayableAmount(invoiceDto);
    }
}
