package com.rocketseat.planner.exception;

import com.rocketseat.planner.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidActivityDateException extends Exception {

    public static final ExceptionDetails ERROR = ExceptionDetails.INVALID_ACTIVITY_DATE_MESSAGE;

    public InvalidActivityDateException(String message) {
        super(ERROR.formatErrorMessage(message));
    }

}
