package com.sadad.doctorappointment.appointment.service;

import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {
    List<AppointmentInfo> findByDateTimeAndDoctor_Id(LocalDate localDate, Long doctorId);

    List<Appointment> setSlots(SlotsRequest request);
}
