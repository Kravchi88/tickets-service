package com.kravchi88.tickets.common.error;

import com.kravchi88.tickets.common.error.exception.InvalidCredentialsException;
import com.kravchi88.tickets.common.error.exception.InvalidRefreshTokenException;
import com.kravchi88.tickets.common.error.exception.LoginAlreadyExistsException;
import com.kravchi88.tickets.common.error.exception.TicketAlreadyPurchasedException;
import com.kravchi88.tickets.common.error.exception.TicketNotFoundException;
import com.kravchi88.tickets.common.error.exception.UserNotFoundException;
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

        e.getConstraintViolations().forEach(violation -> {
            String param = violation.getPropertyPath().toString();
            issues.computeIfAbsent(param, k -> new ArrayList<>()).add(violation.getMessage());
        });

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

    @ExceptionHandler(TicketAlreadyPurchasedException.class)
    public ResponseEntity<ErrorResponse> handleTicketAlreadyPurchased(TicketAlreadyPurchasedException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        issues.put("ticket", List.of(e.getMessage()));

        ErrorResponse body = new ErrorResponse("CONFLICT", "Ticket already purchased", issues);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketNotFound(TicketNotFoundException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        issues.put("ticket", List.of(e.getMessage()));

        ErrorResponse body = new ErrorResponse("NOT FOUND", "Ticket not found", issues);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        issues.put("user", List.of(e.getMessage()));

        ErrorResponse body = new ErrorResponse("NOT FOUND", "User not found", issues);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        issues.put("authentication", List.of(e.getMessage()));

        ErrorResponse body = new ErrorResponse("UNAUTHORIZED", "Authentication required", issues);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRefreshToken(InvalidRefreshTokenException e) {
        Map<String, List<String>> issues = new LinkedHashMap<>();
        issues.put("token", List.of(e.getMessage()));

        ErrorResponse body = new ErrorResponse("UNAUTHORIZED", "Authentication required", issues);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}