package com.module.price.rule.impl;

import com.module.price.rule.DiscountRule;

public class AffiliateDiscountRule implements DiscountRule {

    private static final Double discount = .1;

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * discount;
    }
}
