package com.example.demo.service;

import com.example.demo.converter.UserConverter;
import com.example.demo.dto.UserRequest;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundExcetpion;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AddressService addressService;

    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, AddressService addressService, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.userConverter = userConverter;
    }

    @PostConstruct
    public void init (){
        User user = new User();
//        user.setId(1L);
//        user.setName("Test User");
//        user.setEmail("test@email.com");
//
//        userServiceImpl.save(user);
//
//        Address address = new Address();
//        address.setId(1L);
//        address.setCity("NY");
//        address.setStreet("demo_street");
//        address.setNumber(101 - 55);
//        address.setUser(user);
//
//        addressServiceImpl.save(address);
    }



    @Override
    public User save(User user) {

        log.info("Create new User with email: {}",user.getAddresses());
        List<Address> addresses = user.getAddresses();

        for (Address address : addresses) {
            addressService.save(address);
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {

        List<User> users = userRepository.findAll();
        log.info("Users size: {}",users.size());
        return users;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundExcetpion("No such User with id: "+id));
    }

    @Override
    public User updateUser(Long id, UserRequest userRequest) {

        User user = findById(id);

        User updatedUser = userConverter.update(user, userRequest);

        userRepository.save(updatedUser);

        return updatedUser;
    }

    @Override
    public void deleteById(Long id) {

        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
