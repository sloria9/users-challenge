package com.challenge.users.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserEmailExists extends RuntimeException {

    private static final long serialVersionUID = -875917576558141555L;
    
    public UserEmailExists(final String message) {
        super(message);
    }
}
