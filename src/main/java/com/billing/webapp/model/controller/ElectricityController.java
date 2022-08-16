package com.billing.webapp.model.controller;

import com.billing.webapp.model.dto.AddressDto;
import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.services.ElectricityService;
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

@RestController
@AllArgsConstructor
@RequestMapping("electricity")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ElectricityController {

    private final ElectricityService electricityService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ElectricityDto getById(@PathVariable String id){
        return ElectricityDto.toElectricityDto(electricityService.getById(id));
    }
    @PostMapping
    public String createElectricity(@RequestBody ElectricityDto electricityDto) {
        electricityService.createElectricity(electricityDto);
        return "created";
    }

    @PatchMapping("/{id}")
    public String updateElectricity(@PathVariable String id, @RequestBody ElectricityDto electricityDto) {
        electricityService.updateElectricity(id, electricityDto);
        return "updated";
    }

    @DeleteMapping("/{id}")
    public String deleteElectricity(@PathVariable String id) {
        electricityService.deleteElectricity(id);
        return "deleted";
    }

    @PatchMapping("/{id}/new_data")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String insertMonthAmountSpend(@PathVariable String id, @RequestBody Integer data){
        electricityService.insertMonthSpend(id, data);
        return "inserted";
    }
}
