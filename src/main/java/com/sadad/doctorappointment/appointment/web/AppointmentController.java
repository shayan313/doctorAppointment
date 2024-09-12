package com.sadad.doctorappointment.appointment.web;

import com.sadad.doctorappointment.appointment.dto.AppointmentDto;
import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.mapper.AppointmentMapper;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final IAppointmentService service;
    private final AppointmentMapper appointmentMapper;

    @PostMapping("setSlots")
    public List<AppointmentDto> setSlots(@Valid @RequestBody SlotsRequest request) {
        return service.setSlots(request).stream().map(appointmentMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("availableSlots")
    public Page<Appointment> availableSlots(
            @RequestParam(required = false) Date currentDate,
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "direction", defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(value = "sortProperties", defaultValue = "id") String sortProperty
    ) {
        return service.getAll(currentDate, PageRequest.of(currentPage - 1, pageSize, direction, sortProperty));
    }

   /* @GetMapping("getAll")
    public Page<Appointment> getAll(
            @RequestParam(required = false) Date currentDate,
            @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "direction", defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(value = "sortProperties", defaultValue = "id") String sortProperty,
            ) {
        return appointmentService.getAll(currentDate, PageRequest.of(currentPage - 1, pageSize, direction, sortProperty));
    }*/

}
