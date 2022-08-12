package com.billing.webapp.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewUserDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private  String password;
}
