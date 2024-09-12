package com.sadad.doctorappointment.base.exception;

import lombok.Data;

@Data
public class ErrorDetail {
    private String message;
    private String detail;
}
