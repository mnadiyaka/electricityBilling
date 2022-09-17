package com.billing.webapp.controller;

import com.billing.webapp.model.dto.AddressDto;
import com.billing.webapp.model.dto.ElectricityDto;
import com.billing.webapp.model.entity.Electricity;
import com.billing.webapp.model.entity.ElectricityHolder;
import com.billing.webapp.services.ElectricityService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("electricity")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ElectricityController {

    private ElectricityService electricityService;

    @Autowired
    ElectricityController(ElectricityService electricityService) {
        this.electricityService = electricityService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ElectricityDto getById(@PathVariable String id) {
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

    ///*
    //without Asynch
    @PatchMapping("/{id}/new_data")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String insertMonthAmountSpend(@PathVariable String id, @RequestBody Integer data) {
        electricityService.insertMonthSpend(id, data);
        return "inserted";
    }
    //*/

    @PutMapping("/{id}/new_data")
    public String newData(@PathVariable String id, @RequestBody Integer data) {
        CompletableFuture<Electricity> result = electricityService.asyncInsertNewMonthSpend(id, data);
        String key = UUID.randomUUID().toString();
        ElectricityHolder.setData(key, result);
        return key;
    }

    @GetMapping("/updatedRes")
    @SneakyThrows
    public ResponseEntity<ElectricityDto> newData(@RequestParam String id) {
        CompletableFuture<Electricity> electricity = ElectricityHolder.getData(id);
        if (electricity != null) {
            return new ResponseEntity<>(ElectricityDto.toElectricityDto(electricity.get()), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
