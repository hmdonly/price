package com.module.price.service;

import com.module.price.dto.request.InvoiceDto;

public interface DiscountCalculationService {
    double calculatePayableAmount(InvoiceDto invoiceDto);
}
