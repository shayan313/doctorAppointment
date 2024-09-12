package com.sadad.doctorappointment.appointment.web;

import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final IAppointmentService service;
    @PostMapping("setSlots")
    public void setSlots(@Valid @RequestBody SlotsRequest request ) {
         service.setSlots(request );
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
