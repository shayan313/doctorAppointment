package com.sadad.doctorappointment.appointment.repository;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.projection.AppointmentDoctorInfo;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<AppointmentInfo> findByDateTimeAndDoctor_Id(LocalDate dateTime, Long doctorId);

    @Query("select a from Appointment a where a.doctor.id = :id " +
            " and (:dateTime is null or  a.dateTime = :dateTime  ) " +
            " and  (:status is null or  a.status = :status  ) ")
    List<AppointmentDoctorInfo> findByDoctor_IdAndDateTime(Long id, LocalDate dateTime, AppointmentStatus status);


}