package com.module.price.rule.impl;

import com.module.price.rule.DiscountRule;

public class EmployeeDiscountRule implements DiscountRule {
    private static final Double discount = .30;

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * discount;
    }
}
