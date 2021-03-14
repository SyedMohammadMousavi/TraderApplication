package com.traders.application.items.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ItemCreationFailedException extends RuntimeException{

    public ItemCreationFailedException(String message) {
        super(message);
    }
}
