package com.sadad.doctorappointment.appointment.repository;

import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<AppointmentInfo> findByDateTimeAndDoctor_Id(LocalDate dateTime, Long doctorId);




}