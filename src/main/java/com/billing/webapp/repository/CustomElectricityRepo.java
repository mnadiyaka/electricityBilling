package com.billing.webapp.repository;

import com.billing.webapp.model.dto.ElectricityDto;

import java.util.List;

public interface CustomElectricityRepo {

    List<ElectricityDto> findNotPaidElectricity();
}
