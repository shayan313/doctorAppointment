package com.sadad.doctorappointment.user.service;

import com.sadad.doctorappointment.user.dto.DoctorDto;
import com.sadad.doctorappointment.user.model.Doctor;

public interface IDoctorService {


    Doctor findById(Long doctorId);

    Doctor findByIdForUpdate(Long doctorId);

    DoctorDto saveOrUpdate(DoctorDto dto);
}
