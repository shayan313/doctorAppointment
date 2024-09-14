package com.sadad.doctorappointment.appointment.projection;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;

import java.time.LocalDate;

/**
 * Projection for {@link com.sadad.doctorappointment.appointment.model.Appointment}
 */
public interface AppointmentInfo {

    Long getId();

    LocalDate getDateTime();

    String getStartTime();

    String getEndTime();

    AppointmentStatus getStatus();

    DoctorInfo getDoctor();

    String getPatientName();

    String getPatientPhoneNumber();
}