package com.example.demo.controller.exception;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exception.UserNotFoundExcetpion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundExcetpion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO processMethodArgumentNotValidException(UserNotFoundExcetpion e) {

        String message = "User not found";
        log.error(message, e);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(message);
        errorDTO.setDeveloperMessage(e.getMessage());
        errorDTO.setCode(HttpStatus.NOT_FOUND.value());

        return errorDTO;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO processGeneralException(Exception e) {

        String message = "General backend exception";
        log.error(message, e);

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(message);
        errorDTO.setDeveloperMessage(e.getMessage());
        errorDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return errorDTO;
    }
}
