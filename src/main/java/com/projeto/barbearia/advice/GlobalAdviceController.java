package com.projeto.barbearia.advice;

import com.projeto.barbearia.service.exceptions.ExceptionResponse;
import com.projeto.barbearia.service.exceptions.NotFoundException;
import com.projeto.barbearia.service.exceptions.RoleNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalAdviceController {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException exception, WebRequest webRequest) {
        ExceptionResponse response =
                new ExceptionResponse(LocalDateTime.now(),exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(ResponseStatusException exception, WebRequest webRequest) {
        ExceptionResponse response =
                new ExceptionResponse(LocalDateTime.now(),exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(response, exception.getStatusCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRunTimeException(RuntimeException ex, WebRequest webRequest) {
        ExceptionResponse response =
                new ExceptionResponse(LocalDateTime.now(),ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        ExceptionResponse response =
                new ExceptionResponse(LocalDateTime.now(),ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> genericException(Exception ex, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFound.class)
    public ResponseEntity<ExceptionResponse> handleRoleNotFound(RuntimeException ex, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
