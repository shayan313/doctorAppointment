package com.sadad.doctorappointment.user.projection;

/**
 * Projection for {@link com.sadad.doctorappointment.user.model.Doctor}
 */
public interface DoctorInfo {
    Long getId();

    String getName();

    String getSpecialization();
}