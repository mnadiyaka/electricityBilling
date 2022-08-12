package com.billing.webapp.services;

import com.billing.webapp.model.dto.AddressDto;
import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.model.entity.Address;

import java.util.List;

public interface AddressService {

    List<AddressDto> getAll();

    Address getById(String id);

    Address createAddress(AddressDto addressDto);

    Address updateAddress(String id, AddressDto addressDto);

    void deleteAddress(String id);

    void assignElectricity(String id, String electricityId);

    void assignElectricity(String id, ElectricityDto electricityDto);

    void verifyAddress(String id, boolean verify);

    void approveDiscount (String id, boolean approve);
}