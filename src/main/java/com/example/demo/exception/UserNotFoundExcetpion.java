package com.example.demo.exception;

public class UserNotFoundExcetpion extends RuntimeException {

    public UserNotFoundExcetpion() {
    }

    public UserNotFoundExcetpion(String message) {
        super(message);
    }
}
