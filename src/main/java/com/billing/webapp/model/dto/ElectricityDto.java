package com.billing.webapp.model.dto;

import com.billing.webapp.model.entity.Electricity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ElectricityDto {

    private Double tariff;

    private Integer totalAmountSpend;

    private Integer monthAmountSpend;

    private Double totalPaid;

    private Double monthPaid;

    private Double discount;

    private LocalDate date;

    public static Electricity toElectricity(ElectricityDto electricityDto) {
        return new Electricity()
                .setTariff(electricityDto.getTariff())
                .setTotalAmountSpend(electricityDto.getTotalAmountSpend())
                .setMonthAmountSpend(electricityDto.getMonthAmountSpend())
                .setTotalPaid(electricityDto.getTotalPaid())
                .setMonthPaid(electricityDto.getMonthPaid())
                .setDiscount(electricityDto.getDiscount())
                .setDate(electricityDto.getDate());
    }

    public static ElectricityDto toElectricityDto(Electricity electricity) {
        return new ElectricityDto()
                .setTariff(electricity.getTariff())
                .setTotalAmountSpend(electricity.getTotalAmountSpend())
                .setMonthAmountSpend(electricity.getMonthAmountSpend())
                .setTotalPaid(electricity.getTotalPaid())
                .setMonthPaid(electricity.getMonthPaid())
                .setDiscount(electricity.getDiscount());
    }
}
