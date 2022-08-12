package com.example.authorizationjwt.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class OldPasswordException extends RuntimeException {

    private HttpStatus httpStatus;

    public OldPasswordException(String msg) {
        super(msg);
    }

    public OldPasswordException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
