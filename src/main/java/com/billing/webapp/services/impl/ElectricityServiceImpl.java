package com.billing.webapp.services.impl;

import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.model.entity.Electricity;
import com.billing.webapp.repository.ElectricityRepository;
import com.billing.webapp.services.ElectricityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class ElectricityServiceImpl implements ElectricityService {

    private final ElectricityRepository electricityRepository;

    @Override
    public Electricity getById(String id) {
        return electricityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Electricity with " + id + "doesn't exist"));
    }

    @Override
    public Electricity createElectricity(ElectricityDto ElectricityDto) {
        return null;
    }

    @Override
    public Electricity updateElectricity(String id, ElectricityDto electricityDto) {
        Electricity electricity = getById(id);
        return null;
    }

    @Override
    public void deleteElectricity(String id) {
        Electricity electricity = getById(id);
        electricityRepository.delete(electricity);
    }

    @Override
    public void insertMonthSpend(String id, Integer data) {
        Electricity electricity = getById(id);
        electricity.setTotalAmountSpend(electricity.getMonthAmountSpend());
        electricity.setMonthAmountSpend(data);
        electricity.setTotalPaid(electricity.getTotalPaid() + electricity.getMonthPaid());
        electricity.setMonthPaid(countBill(electricity));

        electricityRepository.insert(electricity);
    }

    @Override
    public void addDiscount(String id, Double discount) {
        Electricity electricity = getById(id);
        electricity.setDiscount(discount);

        electricityRepository.insert(electricity);
    }

    private Double countBill(Electricity electricity) {
        return electricity.getTariff() * (electricity.getMonthAmountSpend() - electricity.getTotalAmountSpend())*(1.0-electricity.getDiscount());
    }
}
