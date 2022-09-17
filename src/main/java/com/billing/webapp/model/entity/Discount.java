package com.billing.webapp.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public record Discount (String id, String type, Double discount){

    public Discount{
        Objects.requireNonNull(type);
        Objects.requireNonNull(discount);
    }

    //ELDERLY_PERSON (0.35), DISABILITY(0.5), POOR(0.4), CHILDREN_3_PLUS(0.3), NONE(1.0);
}
