package com.example.demo.controller;


import com.example.demo.entity.Address;
import com.example.demo.service.AddressServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {

    private final AddressServiceImpl addressServiceImpl;

    public AddressController(AddressServiceImpl addressServiceImpl) {
        this.addressServiceImpl = addressServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {

        List<Address> addressesResponses = new ArrayList<>(addressServiceImpl.findAll());

        return new ResponseEntity<>(addressesResponses, HttpStatus.OK);

    }
}