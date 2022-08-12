package com.billing.webapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Discount {

    ELDERLY_PERSON (0.35), DISABILITY(0.5), POOR(0.4), CHILDREN_3_PLUS(0.3), NONE(1.0);

    private Double discount;
}
