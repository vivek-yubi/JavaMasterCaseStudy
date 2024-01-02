package com.busservice.routeapp.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.*;

import org.springframework.validation.FieldError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errorList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(errors -> {
            Map<String, String> error  = new HashMap<>();
            String fieldName = ((FieldError) errors).getField();
            String errorMessage = errors.getDefaultMessage();
            error.put("error", errorMessage);
            error.put("field_name",fieldName);
            errorList.add(error);
        });
        HashMap<String, Object> result = new HashMap<>();
        result.put("errorList", errorList);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Extract information from the exception and build a response
        List<Map<String, String>> errorList = new ArrayList<>();

        // Extract the root cause exception
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof SQLException) {
            String sqlMessage = rootCause.getMessage();
            String constraintName = extractConstraintNameFromErrorMessage(sqlMessage);

            Map<String, String> error = new HashMap<>();
            error.put("error", "Data integrity violation");
            error.put("constraint_name", constraintName);
            errorList.add(error);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("errorList", errorList);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, String>> errorList = new ArrayList<>();

        // Extract the root cause exception
        Throwable rootCause = ex.getCause();

        if (rootCause instanceof SQLException) {
            String sqlMessage = rootCause.getMessage();
            String constraintName = extractConstraintNameFromErrorMessage(sqlMessage);

            Map<String, String> error = new HashMap<>();
            error.put("error", "Constraint violation");
            error.put("constraint_name", constraintName);
            errorList.add(error);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("errorList", errorList);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String extractConstraintNameFromErrorMessage(String errorMessage) {
        int start = errorMessage.indexOf("Detail:");
        int end = errorMessage.length();
        if (start != -1 && end != -1 && end > start) {
            return errorMessage.substring(start, end);
        }
        return "Unknown Error";
    }


}
