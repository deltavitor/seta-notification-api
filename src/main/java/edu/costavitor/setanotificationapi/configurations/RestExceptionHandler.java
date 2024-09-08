package edu.costavitor.setanotificationapi.configurations;

import edu.costavitor.setanotificationapi.common.exceptions.EntityNotFoundException;
import edu.costavitor.setanotificationapi.common.exceptions.ExceptionWrapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ExceptionWrapper> handleEntityNotFoundException(EntityNotFoundException e) {

        ExceptionWrapper error = new ExceptionWrapper(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }
}
