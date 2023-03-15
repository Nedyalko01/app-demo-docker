package com.example.demo.controller;

import com.example.demo.converter.UserConverter;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final UserConverter userConverter;

    public UserController(UserServiceImpl userServiceImpl, UserConverter userConverter) {
        this.userServiceImpl = userServiceImpl;
        this.userConverter = userConverter;
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@NotNull @RequestBody UserRequest userRequest) {

        User user = userConverter.convertToUser(userRequest);

        User savedUser = userServiceImpl.save(user);

        UserResponse response = userConverter.convertUserToResponse(savedUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {

        List<UserResponse> users = userServiceImpl.findAll()
                .stream()
                .map(userConverter::convertUserToResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        User user = userServiceImpl.updateUser(id, userRequest);

        UserResponse response = userConverter.convertUserToResponse(user);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {

        User user = userServiceImpl.findById(id);

        UserResponse response = userConverter.convertUserToResponse(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteById(@PathVariable Long id) {
        try {
            userServiceImpl.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            throw new IllegalArgumentException("User not found");
        }
    }

    @GetMapping(value = "/sort")
    public Page<User> findAllSorted(@RequestParam int page, @RequestParam int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return userServiceImpl.findAll(pageRequest);

    }

    @GetMapping(value = "/sorted")
    public Page<User> sortedUser(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy) {

        Page<User> sorted = userServiceImpl.findAll(PageRequest.of(page.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id")));

        return sorted;
    }
}