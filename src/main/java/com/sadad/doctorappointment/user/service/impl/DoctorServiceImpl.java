package com.sadad.doctorappointment.user.service.impl;


import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.user.constants.Role;
import com.sadad.doctorappointment.user.dto.DoctorDto;
import com.sadad.doctorappointment.user.mapper.DoctorMapper;
import com.sadad.doctorappointment.user.model.Doctor;
import com.sadad.doctorappointment.user.repository.DoctorRepository;
import com.sadad.doctorappointment.user.service.IDoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Log4j2
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository repository;

    private final DoctorMapper doctorMapper;

    @Override
    public Doctor findById(Long doctorId) {
        return repository.findById(doctorId).orElseThrow(() -> new ApplicationException("doctor.not.found.exception"));
    }

    @Override
    public Doctor findByIdForUpdate(Long doctorId) {
        return repository.findByIdForUpdate(doctorId).orElseThrow(() -> new ApplicationException("doctor.not.found.exception"));
    }


    @Override
    @Transactional
    public DoctorDto saveOrUpdate(DoctorDto dto) {

        if (dto.getFromWorkTimeAsLocalTime().isAfter(dto.getToWorkTimeAsLocalTime())) {
            throw new ApplicationException("doctor.FromTime.isAfter.ToTime");
        }
        if (dto.getId() != null && dto.getId() > 0L) {
            return updateDoctor(dto.getId(), dto);
        } else {
            return saveDoctor(dto);
        }
    }


    @Transactional
    public DoctorDto saveDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctor.setRole(Role.DOCTOR);
        Doctor savedDoctor = repository.save(doctor);
        log.info("updateDoctor Version={} ", savedDoctor.getVersion());
        return doctorMapper.toDto(savedDoctor);
    }

    @Transactional
    public DoctorDto updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor existingDoctor = repository.findByIdForUpdate(id)
                .orElseThrow(() -> new EntityNotFoundException("doctor.not.found.exception"));
        doctorMapper.partialUpdate(doctorDto, existingDoctor);
        Doctor updatedDoctor = repository.save(existingDoctor);
        log.info("updateDoctor Version={} ", updatedDoctor.getVersion());
        return doctorMapper.toDto(updatedDoctor);
    }

}