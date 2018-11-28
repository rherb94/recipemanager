package com.umuc.recipemanager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler({ RecipeNotFoundException.class })
    public ResponseEntity handleApiExceptions(RecipeNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity handleOtherErrors(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
