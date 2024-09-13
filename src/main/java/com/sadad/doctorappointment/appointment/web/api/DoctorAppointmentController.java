package com.sadad.doctorappointment.appointment.web.api;

import com.sadad.doctorappointment.appointment.dto.AppointmentDto;
import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.mapper.AppointmentMapper;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctor/appointment")
@RequiredArgsConstructor
public class DoctorAppointmentController {

    private final IAppointmentService service;
    private final AppointmentMapper appointmentMapper;

    @PostMapping("setSlots")
    @PreAuthorize("hasRole('DOCTOR')")
    public List<AppointmentDto> setSlots(@Valid @RequestBody SlotsRequest request) {
        return service.setSlots(request).stream().map(appointmentMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("availableSlots")
    public List<AppointmentInfo> availableSlots(
            @Schema(type = "LocalDate", implementation = LocalDate.class)
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "تاریخ باید به فرمت yyyy-MM-dd باشد.")
            String currentDate,
            Long doctorId) {
        var localDate = LocalDate.parse(currentDate);
        return service.findByDateTimeAndDoctor_Id(localDate, doctorId);

    }


}
