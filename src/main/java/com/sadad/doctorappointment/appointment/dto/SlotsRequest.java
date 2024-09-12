package com.sadad.doctorappointment.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SlotsRequest {
    @Schema(type = "LocalDate", implementation = LocalDate.class)
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "تاریخ باید به فرمت yyyy-MM-dd باشد.")
    private String currentDate;
    @NotNull(message = "شناسه پرشک را باید وارد نمایید ")
    private Long doctorId;
    @Schema(example = "07:00")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "زمان باید به فرمت HH:mm باشد.")
    private String fromTime;
    @Schema(example = "23:50")
    @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "زمان باید به فرمت HH:mm باشد.")
    private String toTime;

    @JsonIgnore
    public LocalDate getCurrentDateAsLocalDate() {
        return LocalDate.parse(currentDate);
    }

    @JsonIgnore
    public LocalTime getFromTimeAsLocalTime() {
        return LocalTime.parse(fromTime);
    }

    @JsonIgnore
    public LocalTime getToTimeAsLocalTime() {
        return LocalTime.parse(toTime);
    }
}
