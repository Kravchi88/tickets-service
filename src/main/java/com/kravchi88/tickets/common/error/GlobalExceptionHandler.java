package com.kravchi88.tickets.common.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleRequestBodyValidation(MethodArgumentNotValidException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            issues.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
        });

        ErrorResponse body = new ErrorResponse("VALIDATION_ERROR", "Invalid input data", issues);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleRequestParamValidation(ConstraintViolationException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        List<String> messages = new ArrayList<>();

        e.getConstraintViolations().forEach(violation -> messages.add(violation.getMessage()));
        issues.put("params", messages);

        ErrorResponse body = new ErrorResponse("VALIDATION_ERROR", "Invalid input data", issues);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        String param = e.getName();
        String message = "Invalid value for parameter '" + param + "'";
        issues.put("params", List.of(message));

        ErrorResponse body = new ErrorResponse("VALIDATION_ERROR", "Invalid input data", issues);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        issues.put("params", List.of(e.getMessage()));

        ErrorResponse body = new ErrorResponse("VALIDATION_ERROR", "Invalid input data", issues);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateLogin(LoginAlreadyExistsException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        issues.put("login", List.of(e.getMessage()));

        ErrorResponse body = new ErrorResponse("CONFLICT", "Duplicate login", issues);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}