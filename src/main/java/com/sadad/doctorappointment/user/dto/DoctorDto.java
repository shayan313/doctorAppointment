package com.sadad.doctorappointment.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * DTO for {@link com.sadad.doctorappointment.user.model.Doctor}
 */
@Data
public class DoctorDto implements Serializable {

    private Long id;

    @NotNull(message = "نام پرشک را باید وارد نمایید ")
    private String name;
    @Email(message = "ایمیل پزشک را وارد نمایید ")
    private String email;
    @NotNull(message = "شماره تماس پزشک را وارد نمایید ")
    private String phoneNumber;
    private String specialization;
    @NotNull(message = "ساعت شروع به کار پزشک")
    @Schema(example = "23:50")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "زمان باید به فرمت HH:mm باشد.")
    private String fromWorkTime;
    @NotNull(message = "ساعت پایان به کار پزشک")
    @Schema(example = "23:50")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "زمان باید به فرمت HH:mm باشد.")
    private String toWorkTime;


   @JsonIgnore
   public LocalTime getFromWorkTimeAsLocalTime() {
      return LocalTime.parse(fromWorkTime);
   }

   @JsonIgnore
   public LocalTime getToWorkTimeAsLocalTime() {
      return LocalTime.parse(toWorkTime);
   }
}