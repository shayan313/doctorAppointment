package com.sadad.doctorappointment.user.service;

import com.sadad.doctorappointment.user.dto.DoctorDto;
import com.sadad.doctorappointment.user.model.Doctor;
import org.springframework.transaction.annotation.Transactional;

public interface IDoctorService {


    Doctor findById(Long doctorId);

    Doctor findByIdForUpdate(Long doctorId);

    @Transactional
    DoctorDto saveOrUpdate(DoctorDto dto);
}
