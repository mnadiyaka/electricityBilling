package com.billing.webapp.services.impl;

import com.billing.webapp.model.dto.AddressDto;
import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.model.entity.Address;
import com.billing.webapp.repository.AddressRepository;
import com.billing.webapp.services.AddressService;
import com.billing.webapp.services.ElectricityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final ElectricityService electricityService;

    @Override
    public List<AddressDto> getAll() {
        return addressRepository.findAll().stream().map(AddressDto::toAddressDto).collect(Collectors.toList());
    }

    @Override
    public Address getById(String id) {
        return addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address with " + id + "doesn't exist"));
    }

    @Override
    public Address createAddress(AddressDto addressDto) {
        Address address = AddressDto.toAddress(addressDto);
        return addressRepository.insert(address);
    }

    @Override
    public Address updateAddress(String id, AddressDto addressDto) {
        Address address = getById(id);
        address.setRegion(addressDto.getRegion())
                .setDistrict(addressDto.getDistrict())
                .setCityVillage(addressDto.getCityVillage())
                .setStreet(addressDto.getStreet())
                .setBuildingN(addressDto.getBuildingN())
                .setCorpusN(addressDto.getCorpusN())
                .setFlatN(addressDto.getFlatN());
        return addressRepository.insert(address);
    }

    @Override
    public void deleteAddress(String id) {
        Address address = getById(id);
        addressRepository.delete(address);
    }

    @Override
    public void assignElectricity(String id, String electricityId) {
        Address address = getById(id);
        address.setElectricity(electricityService.getById(electricityId));
        addressRepository.insert(address);
    }

    @Override
    public void assignElectricity(String id, ElectricityDto electricityDto) {
        Address address = getById(id);
        address.setElectricity(electricityService.createElectricity(electricityDto));
        addressRepository.insert(address);
    }

    @Override
    public void verifyAddress(String id, boolean verify) {
        Address address = getById(id);
        address.setExists(verify);
        addressRepository.insert(address);
    }

    @Override
    public void approveDiscount(String id, boolean approve) {
        Address address = getById(id);
        address.setDiscountApprove(approve);
        electricityService.addDiscount(address.getElectricity().getId(), address.getDiscount().getDiscount());
        addressRepository.insert(address);
    }
}
