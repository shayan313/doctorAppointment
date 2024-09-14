package com.sadad.doctorappointment.appointment.service;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.appointment.dto.AppointmentRequest;
import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.projection.AppointmentDoctorInfo;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    List<AppointmentInfo> findByDateTimeAndDoctor_Id(LocalDate localDate, Long doctorId);

    List<AppointmentDoctorInfo> findAllByDoctor_Id(Long doctorId, LocalDate localDate, AppointmentStatus status);

    List<Appointment> setSlots(SlotsRequest request);

    void deleteAppointment(Long appointmentId);
    Optional<Appointment> findById(Long appointmentId);

    List<AppointmentInfo> availableAppointment(Long doctorId, LocalDate localDate);


    Appointment takenAppointment(AppointmentRequest request);

    List<AppointmentInfo> getUserAppointments(String phoneNumber, Long doctorId, LocalDate localDate);


}
