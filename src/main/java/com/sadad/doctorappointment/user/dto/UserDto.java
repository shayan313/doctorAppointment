package com.sadad.doctorappointment.user.dto;

import com.sadad.doctorappointment.user.model.UserInfo;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link UserInfo}
 */
@Value
public class UserDto implements Serializable {
    @NotNull(message = "نام خود را وارد نمایید")
    @NotEmpty(message = "نام خود را وارد نمایید ")
    String name;
    @Email(message = "ایمیل خود را وارد نمایید ")
    String email;
    @NotNull(message = "شماره تماس خود را وارد نمایید")
    String phoneNumber;
}