package com.billing.webapp.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
@Accessors(chain = true)
public class Address {

    @Id
    private String id;

    @NotNull
    private String region;

    @NotNull
    private String district;

    @NotNull
    private String cityVillage;

    @NotNull
    private String street;

    @NotNull
    private String buildingN;

    private String corpusN;

    private String flatN;

    private Electricity electricity;

    private Discount discount;

    private boolean discountApprove;

    private boolean exists;
}
