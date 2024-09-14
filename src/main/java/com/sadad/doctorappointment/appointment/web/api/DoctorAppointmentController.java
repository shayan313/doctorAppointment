package com.sadad.doctorappointment.appointment.web.api;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.appointment.dto.AppointmentDto;
import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.mapper.AppointmentMapper;
import com.sadad.doctorappointment.appointment.projection.AppointmentDoctorInfo;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
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
    public List<AppointmentDto> setSlots(@Valid @RequestBody SlotsRequest request) {
        return service.setSlots(request).stream().map(appointmentMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("getAll")
    public List<AppointmentDoctorInfo> getAll(
            @Schema(type = "LocalDate", implementation = LocalDate.class)
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "تاریخ باید به فرمت yyyy-MM-dd باشد.")
            @RequestParam(required = false)
            String currentDate,
            @RequestParam(required = false)
            AppointmentStatus status,
            Long doctorId) {
        var localDate = currentDate != null ? LocalDate.parse(currentDate) : null;
        return service.findAllByDoctor_Id(doctorId, localDate, status);

    }

    @DeleteMapping("/{appointmentId}/delete")
    public void deleteAppointment(@PathVariable Long appointmentId) {
        service.deleteAppointment(appointmentId);

    }


}
