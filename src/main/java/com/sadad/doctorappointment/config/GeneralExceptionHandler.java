/*
package com.sadad.doctorappointment.config;


import com.sadad.doctorappointment.base.exception.ApplicationException;

import com.sadad.doctorappointment.base.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private final Locale locale = Locale.getDefault();

    @ExceptionHandler(ApplicationException.class)
    private ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        return handle(ex);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return handle(ex, "error.exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        return handle(ex, "error.invalid.data.exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<ErrorResponse> handle(Exception ex, String error, HttpStatus httpStatus) {
        internalLog(ex);
        var message = this.messageSource.getMessage(error, null, locale);
        var response = new ErrorResponse();
        response.setError(error);
        response.setMessage(message);
        return new ResponseEntity<>(response, httpStatus);
    }

    private ResponseEntity<ErrorResponse> handle(ApplicationException ex) {
        var response = new ErrorResponse();
        try {
            response.setMessage(this.messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale));
        } catch (NoSuchMessageException exception) {
            response.setMessage(ex.getMessage());
        }
        if (ex.getErrorCode() != null) {
            response.setError(ex.getErrorCode());
        } else {
            response.setError(ex.getMessage());
        }
        if (ex.getErrorBody() != null) {
            response.setDetails(ex.getErrorBody());
        }
        internalLog(ex);
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    private void internalLog(Exception ex) {
        try {
            log.info("inside GeneralExceptionHandler --> unhandled exception");
            log.info(ex.getClass().getName());
            log.info(ex.getMessage());
            log.info(ex.getLocalizedMessage());
            log.error(ex.getMessage(), ex);
            if (ex.getCause() != null)
                log.info(ex.getCause().getMessage());
        } finally {
            log.info("--------------------");
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String message = "";
        boolean includeRequiredNull = false;
        if (ex.getBindingResult() != null) {
            List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
            if (allErrors != null) {
                for (ObjectError e : allErrors) {
                    if (e.getDefaultMessage() != null && !message.contains(e.getDefaultMessage())) {
                        if (e.getCode() != null && e.getCode().equals("NotNull"))
                            includeRequiredNull = true;
                        else
                            message += this.messageSource.getMessage(e.getDefaultMessage(), null, locale);
                    }
                }
            }
        }
        if (includeRequiredNull)
            message += " برخی از مقادیر اجباری وارد نشده است ";
        if (StringUtils.isEmpty(message))
            message = "مقادیر ارسالی معتبر نمی باشند";
        var response = new ErrorResponse();
        response.setError("error.validation");
        response.setMessage(message);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }





}
*/
