package com.sadad.doctorappointment.appointment.projection;

import com.sadad.doctorappointment.user.model.User;

/**
 * Projection for {@link User}
 */
public interface UserInfo {
    Long getId();
    String getFirstName();
    String getLastName();

}