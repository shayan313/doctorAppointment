package com.sadad.doctorappointment.appointment.repository;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.projection.AppointmentDoctorInfo;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<AppointmentInfo> findByDateTimeAndDoctor_Id(LocalDate dateTime, Long doctorId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Appointment a where a.id = :id")
    Optional<Appointment> findByIdWithLock(Long id);


    @Query("select a from Appointment a where a.doctor.id = :doctorId " +
            " and (:dateTime is null or  a.dateTime = :dateTime  ) " +
            " and  (:status is null or  a.status = :status  ) ")
    List<AppointmentDoctorInfo> findByDoctor_IdAndDateTime(Long doctorId, LocalDate dateTime, AppointmentStatus status);

    List<AppointmentInfo> findByDoctor_IdAndDateTimeAndStatus(Long id, LocalDate dateTime, AppointmentStatus status);

}