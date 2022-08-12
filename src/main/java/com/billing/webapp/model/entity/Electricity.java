package com.billing.webapp.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
public class Electricity {

    @Id
    private String id;

    private Double tariff;

    private Integer totalAmountSpend;

    private Integer monthAmountSpend;

    private Double totalPaid;

    private Double monthPaid;

    private Double discount;
}
