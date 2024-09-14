package com.sadad.doctorappointment.appointment.dto;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.appointment.model.Appointment;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Appointment}
 */
@Data
public class AppointmentDto implements Serializable {
    Long id;
    LocalDate dateTime;
    String startTime;
    String endTime;
    AppointmentStatus status;
    Long doctorId;
    String doctorFirstName;
    String doctorLastName;
    String doctorSpecialization;


}