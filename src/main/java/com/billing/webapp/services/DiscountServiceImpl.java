package com.billing.webapp.services.impl;

import com.billing.webapp.model.entity.Discount;
import com.billing.webapp.repository.DiscountRepository;
import com.billing.webapp.services.DiscountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    public Discount init() {
        List<Discount> discounts = new ArrayList<>();
        discounts.add(new Discount( "1","ELDERLY_PERSON",0.35));
        discounts.add(new Discount( "2","DISABILITY",0.5));
        discounts.add(new Discount( "3","POOR",0.4));
        discounts.add(new Discount( "4","CHILDREN_3_PLUS",0.3));
        discounts.add(new Discount( "5","NONE",1.0));
        discountRepository.saveAll(discounts);
        return discounts.get(1);
    }
}
