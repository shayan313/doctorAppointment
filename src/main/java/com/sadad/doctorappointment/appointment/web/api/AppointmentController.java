package com.sadad.doctorappointment.appointment.web.api;

import com.sadad.doctorappointment.appointment.dto.AppointmentDto;
import com.sadad.doctorappointment.appointment.dto.AppointmentRequest;
import com.sadad.doctorappointment.appointment.mapper.AppointmentMapper;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final IAppointmentService service;
    private final AppointmentMapper appointmentMapper;

    @GetMapping("available")
    public List<AppointmentInfo> availableAppointment(
            @Schema(type = "LocalDate", implementation = LocalDate.class)
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "تاریخ باید به فرمت yyyy-MM-dd باشد.")
            String currentDate,
            Long doctorId) {
        var localDate = currentDate != null ? LocalDate.parse(currentDate) : null;
        return service.availableAppointment(doctorId, localDate);
    }

    @PutMapping("/taken")
    public AppointmentDto takenAppointment(AppointmentRequest request) {
        return appointmentMapper.toDto(service.takenAppointment(request));
    }

    @GetMapping("/getUserAppointments")
    public List<AppointmentInfo> getUserAppointments(
            String phoneNumber,
            @Schema(type = "LocalDate", implementation = LocalDate.class)
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "تاریخ باید به فرمت yyyy-MM-dd باشد.")
            @RequestParam(required = false) String currentDate,
            @RequestParam(required = false) Long doctorId) {

        var localDate = currentDate != null ? LocalDate.parse(currentDate) : null;
        return service.getUserAppointments(phoneNumber, doctorId, localDate);
    }

}
