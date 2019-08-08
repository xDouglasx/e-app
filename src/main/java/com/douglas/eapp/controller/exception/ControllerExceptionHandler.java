package com.douglas.eapp.controller.exception;


import com.douglas.eapp.controller.exception.model.StandardError;
import com.douglas.eapp.controller.exception.model.ValidationError;
import com.douglas.eapp.exception.AuthorizationException;
import com.douglas.eapp.exception.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {
    StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), exception.getMessage(),
        System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<StandardError> dataIntegrity(DataIntegrityViolationException exception,
      HttpServletRequest request) {
    StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
        System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException exception,
      HttpServletRequest request) {
    ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation Error",
        System.currentTimeMillis());
    exception.getBindingResult().getFieldErrors()
        .forEach(error -> err.addError(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<StandardError> objectNotFound(AuthorizationException exception, HttpServletRequest request) {
    StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), exception.getMessage(),
        System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).  body(err);
  }
}
