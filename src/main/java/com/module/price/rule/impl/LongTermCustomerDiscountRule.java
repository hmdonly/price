package com.module.price.rule.impl;

import com.module.price.rule.DiscountRule;

public class LongTermCustomerDiscountRule implements DiscountRule {
    private static final Double discount = .05;

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * discount;
    }
}
