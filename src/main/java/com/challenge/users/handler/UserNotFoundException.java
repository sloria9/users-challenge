package com.challenge.users.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception{

    private static final long serialVersionUID = 2461547935659344532L;

    public UserNotFoundException(final String message) {
        super(message);
    }
}
