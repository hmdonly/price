package com.module.price.service.impl;

import com.module.price.client.CurrencyExchangeClient;
import com.module.price.dto.request.InvoiceDto;
import com.module.price.dto.request.ItemDto;
import com.module.price.enums.ItemCategory;
import com.module.price.enums.UserType;
import com.module.price.rule.DiscountRule;
import com.module.price.rule.impl.*;
import com.module.price.service.DiscountCalculationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiscountCalculationServiceImpl implements DiscountCalculationService {

    private final Map<UserType, DiscountRule> discountRules = new HashMap<>();

    private final CurrencyExchangeClient currencyExchangeClient;

    public DiscountCalculationServiceImpl(CurrencyExchangeClient currencyExchangeClient) {
        discountRules.put(UserType.AFFILIATE, new AffiliateDiscountRule());
        discountRules.put(UserType.EMPLOYEE, new EmployeeDiscountRule());
        discountRules.put(UserType.LONG_TERM_CUSTOMER, new LongTermCustomerDiscountRule());
        this.currencyExchangeClient = currencyExchangeClient;
    }

    @Override
    public double calculatePayableAmount(InvoiceDto invoiceDto) {
        double totalAmount = invoiceDto.getTotalAmount();
        double totalNonGroceryAmount = 0;

        for (ItemDto item : invoiceDto.getItems()) {
            if (item.getCategory() != ItemCategory.GROCERY) {
                totalNonGroceryAmount += item.getTotalAmount();
            }
        }

        double percentageDiscount = 0;
        double fixedDiscount = 0;

        UserType userType = invoiceDto.getUserDto().getUserType();

        if ((UserType.EMPLOYEE).equals(userType)) {
            percentageDiscount = discountRules.get(UserType.EMPLOYEE).applyDiscount(totalNonGroceryAmount);
        } else if ((UserType.AFFILIATE).equals(userType)){
            percentageDiscount = discountRules.get(UserType.AFFILIATE).applyDiscount(totalNonGroceryAmount);
        } else if (invoiceDto.getUserDto().getCustomerTenure() > 24) { // More than 2 years
            percentageDiscount = discountRules.get(UserType.LONG_TERM_CUSTOMER).applyDiscount(totalNonGroceryAmount);
        }

        fixedDiscount += new NormalDiscountRule().applyDiscount(totalAmount); // $5 for every $100

       double totalDiscount = percentageDiscount + fixedDiscount;

       totalAmount -= totalDiscount;
       double exchangeRate = currencyExchangeClient.getExchangeRate(invoiceDto.getOriginalCurrency().getCode(), invoiceDto.getTargetCurrency().getCode());
       return totalAmount * exchangeRate;
    }

}

