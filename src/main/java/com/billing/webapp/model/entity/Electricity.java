package com.billing.webapp.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Data
@Document
@Accessors(chain = true)
public class Electricity {

    @Id
    private String id;

    private Double tariff;

    private Integer totalAmountSpend;

    private Integer monthAmountSpend;

    private Double totalPaid;

    private Double monthPaid;

    private Double toPay;

    private Double discount;

    private LocalDate date;

    private Set<History> history;
}
