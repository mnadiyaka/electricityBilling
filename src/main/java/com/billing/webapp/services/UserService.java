package com.billing.webapp.services;

import com.billing.webapp.model.dto.NewUserDto;
import com.billing.webapp.model.dto.UserDto;
import com.billing.webapp.model.entity.Role;
import com.billing.webapp.model.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    User getById(String id);

    void createUser(NewUserDto userDto);

    User updateUser(String id, UserDto userDto);

    void deleteUser(String id);

    void assignRole(String id, Role role);

    void addAddress(String id, String addressId);
}
