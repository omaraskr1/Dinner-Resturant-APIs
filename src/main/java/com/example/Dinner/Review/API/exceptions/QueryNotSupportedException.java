package com.example.Dinner.Review.API.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QueryNotSupportedException extends Exception {

    public QueryNotSupportedException(String message) {
        super(message);
    }
}
