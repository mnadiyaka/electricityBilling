package com.billing.webapp.services;

import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.model.entity.Electricity;

public interface ElectricityService {

    Electricity getById(String id);

    Electricity createElectricity(ElectricityDto ElectricityDto);

    Electricity updateElectricity(String id, ElectricityDto electricityDto);

    void deleteElectricity(String id);

    void insertMonthSpend(String id, Integer data);

    void addDiscount(String id, Double discount);
}
