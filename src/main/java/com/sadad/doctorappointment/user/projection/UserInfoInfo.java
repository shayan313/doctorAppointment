package com.sadad.doctorappointment.user.projection;

import java.util.Date;

/**
 * Projection for {@link com.sadad.doctorappointment.user.model.UserInfo}
 */
public interface UserInfoInfo {
    Date getCreatedDate();

    Date getModifiedDate();

    Long getId();

    String getName();

    String getEmail();

    String getPhoneNumber();
}