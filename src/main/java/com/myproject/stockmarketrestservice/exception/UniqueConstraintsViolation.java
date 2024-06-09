package com.myproject.stockmarketrestservice.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class UniqueConstraintsViolation extends BindException {
    public UniqueConstraintsViolation(BindingResult bindingResult) {
        super(bindingResult);
    }
}
