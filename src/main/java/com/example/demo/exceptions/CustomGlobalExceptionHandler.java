package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        System.out.println(ex.getFieldErrors());
        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

    @ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
		try {
			List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            body.put("errors", messages);
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    @ExceptionHandler(UpdatedUserException.class)
	protected ResponseEntity<?> handleUpdatedUserException(UpdatedUserException ex, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
		try {
			List<String> messages = new ArrayList<String>();
            messages.add(ex.getMessage());
            body.put("errors", messages);
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}

