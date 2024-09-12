package com.sadad.doctorappointment.config;

import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.base.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return ResponseEntity.badRequest().body(response.build());
    }
}
