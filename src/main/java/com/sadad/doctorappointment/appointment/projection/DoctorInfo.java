package com.sadad.doctorappointment.appointment.projection;

import com.sadad.doctorappointment.doctor.model.Doctor;

/**
 * Projection for {@link Doctor}
 */
public interface DoctorInfo {
    String getSpecialization();
    UserInfo getUser();
}