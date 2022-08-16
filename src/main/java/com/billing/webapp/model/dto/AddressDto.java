package com.billing.webapp.model.dto;

import com.billing.webapp.model.entity.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class AddressDto {

    @NotNull
    private String region;

    @NotNull
    private String district;

    @NotNull
    private String cityVillage;

    @NotNull
    private String street;

    @NotNull
    private String buildingN;

    private String corpusN;

    private String flatN;

    public static Address toAddress(AddressDto addressDto){
        return new Address()
                .setRegion(addressDto.getRegion())
                .setDistrict(addressDto.getDistrict())
                .setCityVillage(addressDto.getCityVillage())
                .setStreet(addressDto.getStreet())
                .setBuildingN(addressDto.getBuildingN())
                .setCorpusN(addressDto.getCorpusN())
                .setFlatN(addressDto.getFlatN());
    }

    public static AddressDto toAddressDto(Address address){
        return new AddressDto()
                .setRegion(address.getRegion())
                .setDistrict(address.getDistrict())
                .setCityVillage(address.getCityVillage())
                .setStreet(address.getStreet())
                .setBuildingN(address.getBuildingN())
                .setCorpusN(address.getCorpusN())
                .setFlatN(address.getFlatN());
    }
}
