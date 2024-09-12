package com.sadad.doctorappointment.user.service.impl;

import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.user.constants.Role;
import com.sadad.doctorappointment.user.dto.DoctorDto;
import com.sadad.doctorappointment.user.mapper.DoctorMapper;
import com.sadad.doctorappointment.user.model.Doctor;
import com.sadad.doctorappointment.user.repository.DoctorRepository;
import com.sadad.doctorappointment.user.service.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper doctorMapper;

    @Override
    public Doctor findById(Long doctorId) {
        return repository.findById(doctorId).orElseThrow(() -> new ApplicationException("doctor.not.found.exception"));
    }


    @Override
    @Transactional
    public DoctorDto saveOrUpdate(DoctorDto dto) {
        
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
        return doctorMapper.toDto(savedDoctor);
    }

    @Transactional
    public DoctorDto updateDoctor(Long id, DoctorDto doctorDto) {
        Doctor existingDoctor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("doctor.not.found.exception"));
        doctorMapper.partialUpdate(doctorDto, existingDoctor);
        Doctor updatedDoctor = repository.save(existingDoctor);
        return doctorMapper.toDto(updatedDoctor);
    }

}