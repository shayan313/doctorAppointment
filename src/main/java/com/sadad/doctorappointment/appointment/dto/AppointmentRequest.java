package com.sadad.doctorappointment.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequest {

    @NotNull(message = "زمان ملاقات  را وارد نمایید")
    private Long appointmentId;

    @NotNull(message = "نام بیمار را باید وارد نمایید ")
    private String name;

    @NotNull(message = "شماره تماس بیمار را وارد نمایید ")
    private String phoneNumber;


}
