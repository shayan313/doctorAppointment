package com.sadad.doctorappointment.appointment.projection;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;

import java.time.LocalDate;
import java.util.Date;

/**
 * Projection for {@link com.sadad.doctorappointment.appointment.model.Appointment}
 */
public interface AppointmentDoctorInfo {
    Date getCreatedDate();

    Date getModifiedDate();

    Long getId();

    LocalDate getDateTime();

    String getStartTime();

    String getEndTime();

    AppointmentStatus getStatus();

    String getPatientName();

    String getPatientPhoneNumber();

}