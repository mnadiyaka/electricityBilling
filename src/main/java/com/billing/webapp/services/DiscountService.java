package com.billing.webapp.services;

import com.billing.webapp.model.entity.Discount;

public sealed interface DiscountService permits DiscountServiceImpl {
    Discount init();

    Discount findById(String id);
}
