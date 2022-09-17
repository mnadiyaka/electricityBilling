package com.billing.webapp.services.impl;

import com.billing.webapp.model.dto.NewUserDto;
import com.billing.webapp.model.dto.UserDto;
import com.billing.webapp.model.entity.Address;
import com.billing.webapp.model.entity.Role;
import com.billing.webapp.model.entity.User;
import com.billing.webapp.repository.UserRepository;
import com.billing.webapp.services.AddressService;
import com.billing.webapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final AddressService addressService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().filter(u -> u.getRole() == Role.USER.getGrantedAuthorities()).map(UserDto::toUserDto).collect(Collectors.toList());
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with " + id + "doesn't exist"));
    }

    @Override
    public void createUser(NewUserDto userDto) {
        if (userRepository.findUserByEmail(userDto.getEmail()).isPresent()) {
            throw new EntityExistsException();
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER.getGrantedAuthorities());
        user.setAddresses(new TreeSet<>());
        userRepository.insert(user);
    }

    @Override
    public User updateUser(String id, UserDto userDto) {
        User user = getById(id);
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        return userRepository.insert(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = getById(id);
        userRepository.delete(user);
    }

    @Override
    public void assignRole(String id, Role role) {
        User user = getById(id);
        user.setRole(role.getGrantedAuthorities());
        userRepository.insert(user);
    }

    @Override
    public void addAddress(String id, String addressId) {
        User user = getById(id);
        Set<Address> addresses = user.getAddresses();
        Address address = addressService.getById(addressId);
        if (!address.isExists()) {
            throw new EntityExistsException("This address is not approved yet");
        }
        addresses.add(addressService.getById(addressId));
        userRepository.insert(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new EntityExistsException("User with " + username + "doesn't exist"));
    }
}
