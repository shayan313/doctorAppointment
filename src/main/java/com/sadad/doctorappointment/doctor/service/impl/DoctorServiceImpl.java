package com.sadad.doctorappointment.doctor.service.impl;


import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.doctor.dto.DoctorDto;
import com.sadad.doctorappointment.doctor.mapper.DoctorMapper;
import com.sadad.doctorappointment.doctor.model.Doctor;
import com.sadad.doctorappointment.doctor.repository.DoctorRepository;
import com.sadad.doctorappointment.user.model.User;
import com.sadad.doctorappointment.user.repository.UserRepository;
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
    private final UserRepository userRepository ;
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
        var existingDoctor = repository.findByIdForUpdate(dto.getUserId());
        if (existingDoctor.isPresent()) {
            return updateDoctor(existingDoctor.get(), dto);
        }else {
            return saveDoctor(dto);
        }

    }


    @Transactional
    public DoctorDto saveDoctor(DoctorDto doctorDto) {

        User user = userRepository.findById(doctorDto.getUserId())
                .orElseThrow( () -> new EntityNotFoundException("user.not.found.exception"));
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctor.setUser(user);
        Doctor savedDoctor = repository.save(doctor);
        log.info("updateDoctor Version={} ", savedDoctor.getVersion());
        return doctorMapper.toDto(savedDoctor);
    }

    @Transactional
    public DoctorDto updateDoctor(Doctor existingDoctor, DoctorDto doctorDto) {
        doctorMapper.partialUpdate(doctorDto, existingDoctor);
        Doctor updatedDoctor = repository.save(existingDoctor);
        log.info("updateDoctor Version={} ", updatedDoctor.getVersion());
        return doctorMapper.toDto(updatedDoctor);
    }

}