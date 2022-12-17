package org.psu.lab5.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.psu.lab5.pojo.ValidationErrorResponse;
import org.psu.lab5.pojo.ValidationViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidExcetionHandler(
                        MethodArgumentNotValidException exception) {
                List<ValidationViolation> violations = exception.getBindingResult().getFieldErrors().stream()
                                .map(
                                                error -> new ValidationViolation(
                                                                error.getField(),
                                                                error.getDefaultMessage()))
                                .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ValidationErrorResponse(violations));
        }
}
