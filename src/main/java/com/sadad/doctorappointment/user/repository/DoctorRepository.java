package com.sadad.doctorappointment.user.repository;

import com.sadad.doctorappointment.user.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}