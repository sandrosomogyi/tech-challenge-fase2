package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    private StandardError standardError = new StandardError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound (
            ControllerNotFoundException controllerNotFoundException,
            HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Entity not found");
        standardError.setMessage(controllerNotFoundException.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.standardError);
    }

    @ExceptionHandler(ControllerDatabaseException.class)
    public ResponseEntity<StandardError> databaseException (
            ControllerDatabaseException controllerDatabaseException,
            HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Database Exception");
        standardError.setMessage(controllerDatabaseException.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.standardError);
    }

    @ExceptionHandler(ControllerMessagingException.class)
    public ResponseEntity<StandardError> messagingException (
            ControllerMessagingException controllerMessagingException,
            HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        standardError.setTimeStamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Messaging Exception");
        standardError.setMessage(controllerMessagingException.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.standardError);
    }
}
