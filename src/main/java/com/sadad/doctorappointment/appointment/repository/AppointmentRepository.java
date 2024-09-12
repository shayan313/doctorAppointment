package com.sadad.doctorappointment.appointment.repository;

import com.sadad.doctorappointment.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


}