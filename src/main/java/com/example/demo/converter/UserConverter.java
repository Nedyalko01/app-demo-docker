package com.example.demo.converter;

import com.example.demo.dto.AddressRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final AddressConverter addressConverter;

    public UserConverter(AddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    public User convertToUser(UserRequest userRequest) {
        // TODO https://mapstruct.org/documentation/installation/
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        BeanUtils.copyProperties(userRequest,user);

        List<Address> collectedAddresses = userRequest
                .getAddressRequests()
                .stream()
                .map(addressConverter::convertToAddress).collect(Collectors.toList());

        user.setAddresses(collectedAddresses);
        return user;
    }

    public List<Address> addressFromUser(User user) {

        List<Address> addresses = new ArrayList<>(user.getAddresses());
        return addresses;
    }

    public User update(User user, UserRequest userRequest) {

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setAddresses(addressFromUser(user));
        return user;
    }

    private List<Address> addressesFromDB(User user) {

        List<Address> addresses = new ArrayList<>(user.getAddresses());
        return addresses;
    }

    public UserResponse convertUserToResponse(User user) {

        UserResponse response = new UserResponse();
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setAddresses(addressesFromDB(user));

        return response;

    }

}
