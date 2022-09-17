package com.billing.webapp.services.impl;

import com.billing.webapp.model.dto.AddressDto;
import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.model.entity.Address;
import com.billing.webapp.repository.AddressRepository;
import com.billing.webapp.services.AddressService;
import com.billing.webapp.services.DiscountService;
import com.billing.webapp.services.ElectricityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final ElectricityService electricityService;

    private final DiscountService discountService;

    @Override
    public List<AddressDto> getAll() {
        return addressRepository.findAll().stream().map(AddressDto::toAddressDto).collect(Collectors.toList());
    }

    @Override
    public Address getById(String id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("""
                        Address with 
                        %s
                        doesn't exist
                        """, id)
                ));
    }

    @Override
    public Address createAddress(AddressDto addressDto) {
        Address address = AddressDto.toAddress(addressDto);
        return addressRepository.insert(address);
    }

    @Override
    public Address updateAddress(String id, AddressDto addressDto) {
        Address address = getById(id);
        Optional.ofNullable(addressDto.getRegion()).ifPresent(address::setRegion);
        Optional.ofNullable(addressDto.getDistrict()).ifPresent(address::setDistrict);
        Optional.ofNullable(addressDto.getCityVillage()).ifPresent(address::setCityVillage);
        Optional.ofNullable(addressDto.getStreet()).ifPresent(address::setStreet);
        Optional.ofNullable(addressDto.getBuildingN()).ifPresent(address::setBuildingN);
        Optional.ofNullable(addressDto.getCorpusN()).ifPresent(address::setCorpusN);
        Optional.ofNullable(addressDto.getFlatN()).ifPresent(address::setFlatN);

        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(String id) {
        Address address = getById(id);
        addressRepository.delete(address);
    }

    @Override
    public void assignElectricity(String id, ElectricityDto electricityDto) {
        Address address = getById(id);
        address.setElectricity(electricityService.createElectricity(electricityDto));
        addressRepository.save(address);
    }

    @Override
    public void addDiscount(String id, String discountId) {
        Address address = getById(id);
        address.setDiscount(discountService.findById(discountId));
    }

    @Override
    public void approveDiscount(String id, boolean approve) {
        Address address = getById(id);
        address.setDiscountApprove(approve);
        electricityService.addDiscount(address.getElectricity().getId(), address.getDiscount().discount());
        addressRepository.save(address);
    }
}
