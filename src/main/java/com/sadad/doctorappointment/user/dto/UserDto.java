package com.sadad.doctorappointment.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link com.sadad.doctorappointment.user.model.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    Long id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    boolean enabled;
    Timestamp lastPasswordResetDate;
}