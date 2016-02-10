package com.elennaro.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ControllerAdvice
@RequestMapping(consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE})
public class ControllerAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(value = BindException.class)
    public @ResponseBody ResponseEntity<Map> handleBindingExceptions(BindException ex) {
        logger.warn("Validation exception.", ex);
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, String> errorsMap = new HashMap<>();

        for (FieldError error : fieldErrors) {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity(errorsMap, BAD_REQUEST);
    }
}