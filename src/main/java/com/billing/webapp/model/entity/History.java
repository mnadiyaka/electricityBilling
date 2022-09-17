package com.billing.webapp.model.entity;

import java.time.LocalDate;

public record History(LocalDate date, Integer monthSpend, Double pricePaid) {
}
