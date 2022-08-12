package com.billing.webapp.model.dto;

import com.billing.webapp.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class UserDto {

    @NotNull
    @Email
    private String email;

    Set<AddressDto> addresses;

    public static UserDto toUserDto(User user){
        return new UserDto()
                .setEmail(user.getEmail())
                .setAddresses(user.getAddresses().stream().map(AddressDto::toAddressDto).collect(Collectors.toSet()));
    }
}
