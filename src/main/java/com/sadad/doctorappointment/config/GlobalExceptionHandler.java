package com.sadad.doctorappointment.config;

import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.base.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private final Locale locale = Locale.getDefault();

    @ExceptionHandler(ApplicationException.class)
    private ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        return handle(ex);
    }

    private ResponseEntity<ErrorResponse> handle(ApplicationException ex) {
        var response = ErrorResponse.builder();
        try {
            response.message(this.messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale));
        } catch (NoSuchMessageException exception) {
            response.message(ex.getMessage());
        }
        if (ex.getErrorCode() != null) {
            response.error(ex.getErrorCode());
        } else {
            response.error(ex.getMessage());
        }
        if (ex.getErrorBody() != null) {
            response.details(ex.getErrorBody());
        }
        return new ResponseEntity<>(response.build(), ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var response = ErrorResponse.builder();
        try {
            response.message(this.messageSource.getMessage("validation.method.argument.not.valid", null, locale));
            response.error("validation.method.argument.not.valid");
        } catch (NoSuchMessageException exception) {
            response.message("MethodArgumentNotValid");
        }

        response.error("validation.method.argument.not.valid");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        if (!errors.isEmpty()) {
            response.details(errors);

        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(response.build());
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockException(OptimisticLockException ex) {
        var response = ErrorResponse.builder();

        try {
            response.message(this.messageSource.getMessage("conflict.is.being.modified.by.another.transaction", null, locale));
        } catch (NoSuchMessageException exception) {
            response.message("Conflict: this is being modified by another transaction.");
        }

        response.error("conflict.is.being.modified.by.another.transaction");
        response.details(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response.build());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        var response = ErrorResponse.builder();

        try {
            response.message(this.messageSource.getMessage("database.data.integrity.violation.exception", null, locale));
        } catch (NoSuchMessageException exception) {
            response.message("Database error occurred DataIntegrityViolationException");
        }

        response.error("database.data.integrity.violation.exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.build());

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        var response = ErrorResponse.builder();

        try {
            response.message(this.messageSource.getMessage(ex.getMessage(), null, locale));
        } catch (NoSuchMessageException exception) {
            response.message(ex.getMessage());
        }

        response.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.build());

    }


}
