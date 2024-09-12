package com.sadad.doctorappointment.base.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String error;
    private Object details;
}
