package com.sadad.doctorappointment.appointment.projection;

/**
 * Projection for {@link com.sadad.doctorappointment.user.model.Doctor}
 */
public interface DoctorInfo {
    Long getId();

    String getName();

    String getSpecialization();
}