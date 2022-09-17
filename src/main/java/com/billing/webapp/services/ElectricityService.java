package com.billing.webapp.services;

import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.model.entity.Electricity;
import com.billing.webapp.model.entity.History;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface ElectricityService {

    Electricity getById(String id);

    Electricity createElectricity(ElectricityDto ElectricityDto);

    Electricity updateElectricity(String id, ElectricityDto electricityDto);

    void deleteElectricity(String id);

    void insertMonthSpend(String id, Integer data);

    void addDiscount(String id, Double discount);

    List<ElectricityDto> unpaidThroughMonth(int month);

    String pay(String id, Double price);

    Set<History> showHistory(String id);

    CompletableFuture<Electricity> asyncInsertNewMonthSpend(String id, Integer data);
}
