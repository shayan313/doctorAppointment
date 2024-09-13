package com.sadad.doctorappointment.doctor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sadad.doctorappointment.doctor.model.Doctor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * DTO for {@link Doctor}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto implements Serializable {

    @NotNull(message = "کاربر پزشک پزشک")
    private Long userId;
    private String specialization;
    @NotNull(message = "ساعت شروع به کار پزشک")
    @Schema(example = "23:50")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "زمان باید به فرمت HH:mm باشد.")
    private String startWorkTime;
    @NotNull(message = "ساعت پایان به کار پزشک")
    @Schema(example = "23:50")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "زمان باید به فرمت HH:mm باشد.")
    private String endWorkTime;


   @JsonIgnore
   public LocalTime getFromWorkTimeAsLocalTime() {
      return LocalTime.parse(startWorkTime);
   }

   @JsonIgnore
   public LocalTime getToWorkTimeAsLocalTime() {
      return LocalTime.parse(endWorkTime);
   }
}