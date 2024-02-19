package com.enessefacetin.interviewnexus.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotFoundException(EntityNotFoundException exception) {
        
        logger.warn("Entity not found!", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {

        logger.error("Runtime error: ", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
