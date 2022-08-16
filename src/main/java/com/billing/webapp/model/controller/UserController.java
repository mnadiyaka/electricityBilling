package com.billing.webapp.model.controller;

import com.billing.webapp.model.dto.AddressDto;
import com.billing.webapp.model.dto.NewUserDto;
import com.billing.webapp.model.dto.UserDto;
import com.billing.webapp.model.entity.Role;
import com.billing.webapp.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> getUsers() {
        return userService.getAll();
    }

    @PostMapping("/signUp")
    public String createUser(@RequestBody NewUserDto newUserDto) {
        userService.createUser(newUserDto);
        return "created";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        return "updated";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "deleted";
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String assignRole(@PathVariable String id, @RequestBody Role role) {
        userService.assignRole(id, role);
        return "updated";
    }

    @PatchMapping("/{id}/address/{addressId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String addAddress(@PathVariable String id, @PathVariable String addressId) {
        userService.addAddress(id, addressId);
        return "address added";
    }
}
