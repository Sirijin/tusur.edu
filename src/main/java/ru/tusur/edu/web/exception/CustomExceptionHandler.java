package ru.tusur.edu.web.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tusur.edu.logger.Log;
import ru.tusur.edu.logger.LogFactory;
import ru.tusur.edu.web.exception.response.ErrorResponse;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Log log = LogFactory.api.get(CustomExceptionHandler.class);

    @Value("${server.response.error.trace.enabled}")
    private boolean traceEnabled;

    @ExceptionHandler
    public ResponseEntity<?> handle(Exception ex) {
        log.error(ex.getMessage(), ex);
        log.debug("Class - {}, message - {}, cause - {},  stack trace - {}", ex.getClass(), ex.getMessage(), ex.getCause(), ex.getStackTrace());
        return new ResponseEntity<>(new ErrorResponse(ex, HttpStatus.BAD_REQUEST.value(), traceEnabled), HttpStatus.BAD_REQUEST);
    }
}
