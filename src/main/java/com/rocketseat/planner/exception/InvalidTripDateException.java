package com.rocketseat.planner.exception;

import com.rocketseat.planner.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTripDateException extends Exception {

    public static final ExceptionDetails ERROR = ExceptionDetails.INVALID_TRIP_DATE_MESSAGE;

    public InvalidTripDateException(String message) {
        super(ERROR.formatErrorMessage(message));
    }

}
