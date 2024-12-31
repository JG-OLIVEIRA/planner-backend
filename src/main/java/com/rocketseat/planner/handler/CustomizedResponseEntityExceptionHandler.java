package com.rocketseat.planner.handler;

import com.rocketseat.planner.exception.ExceptionResponse;
import com.rocketseat.planner.exception.InvalidActivityDateException;
import com.rocketseat.planner.exception.InvalidTripDateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidTripDateException.class,
            InvalidActivityDateException.class})
    public final ResponseEntity<ExceptionResponse> handlerBadRequestException(
            Exception ex,
            WebRequest webRequest
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}