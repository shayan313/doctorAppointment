package com.sadad.doctorappointment.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationException extends RuntimeException {
    private String[] args;
    private HttpStatus httpStatus;
    private String errorCode;
    private Object errorBody;

    public ApplicationException() {
        super("error.application.exception");
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ApplicationException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ApplicationException(String message, String... args) {
        super(message);
        this.args = args;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ApplicationException(String message, HttpStatus httpStatus, String... args) {
        super(message);
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public ApplicationException(String message, String errorCode, HttpStatus httpStatus, String... args) {
        super(message);
        this.httpStatus = httpStatus;
        this.args = args;
        this.errorCode = errorCode;
    }

    public Object getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(Object errorBody) {
        this.errorBody = errorBody;
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
