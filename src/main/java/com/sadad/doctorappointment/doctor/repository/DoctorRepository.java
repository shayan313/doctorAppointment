package com.sadad.doctorappointment.doctor.repository;

import com.sadad.doctorappointment.doctor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    @Override
    Optional<Doctor> findById(Long aLong);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select d from Doctor d where d.id = :id")
    Optional<Doctor> findByIdForUpdate(Long id);

}