package com.sadad.doctorappointment.user.service;

import com.sadad.doctorappointment.doctor.dto.DoctorDto;
import com.sadad.doctorappointment.doctor.model.Doctor;

public interface IDoctorService {


    Doctor findById(Long doctorId);

    Doctor findByIdForUpdate(Long doctorId);

    DoctorDto saveOrUpdate(DoctorDto dto);
}
