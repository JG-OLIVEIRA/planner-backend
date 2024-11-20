package com.rocketseat.planner.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public enum ExceptionDetails {

    INVALID_TRIP_DATE_MESSAGE("A data de começo da viagem deveria ser inferior à data de término da viagem", HttpStatus.BAD_REQUEST);

    @Getter
    private final HttpStatus httpStatus;

    private final String message;

    ExceptionDetails(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String formatErrorMessage(String message){
        return String.format(this.message, message);
    }

}
