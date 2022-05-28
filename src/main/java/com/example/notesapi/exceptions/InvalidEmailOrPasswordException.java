package com.example.notesapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmailOrPasswordException extends RuntimeException{


    public InvalidEmailOrPasswordException(String msg) {
        super(msg);
    }
}
