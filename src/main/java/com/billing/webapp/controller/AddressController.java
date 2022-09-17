package com.billing.webapp.controller;

import com.billing.webapp.model.dto.AddressDto;
import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.services.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<AddressDto> getAll() {
        return addressService.getAll();
    }

    @PostMapping
    public String createAddress(@RequestBody AddressDto addressDto) {
        addressService.createAddress(addressDto);
        return "created";
    }

    @PatchMapping("/{id}")
    public String updateAddress(@PathVariable String id, @RequestBody AddressDto addressDto) {
        addressService.updateAddress(id, addressDto);
        return "updated";
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable String id) {
        addressService.deleteAddress(id);
        return "deleted";
    }

    @PatchMapping("/{id}/electricity")
    public String assignElectricity(@PathVariable String id, @RequestBody ElectricityDto electricity) {
        addressService.assignElectricity(id, electricity);
        return "assigned";
    }
}