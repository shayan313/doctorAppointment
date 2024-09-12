package com.sadad.doctorappointment.appointment.service;

import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface IAppointmentService {


    Page<Appointment> getAll(Date currentDate, Pageable pageable);

    void setSlots(SlotsRequest request);
}
