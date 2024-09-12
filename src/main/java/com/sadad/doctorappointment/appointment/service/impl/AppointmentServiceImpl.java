package com.sadad.doctorappointment.appointment.service.impl;

import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.repository.AppointmentRepository;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.user.service.IDoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository repository;
    private final IDoctorService iDoctorService;

    @Override
    public Page<Appointment> getAll(Date currentDate, Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public void setSlots(SlotsRequest request) {

        if (request.getFromTimeAsLocalTime().isAfter(request.getToTimeAsLocalTime())) {
            throw new ApplicationException("application.FromTime.isAfter.ToTime");
        }
        var doctor = iDoctorService.findById(request.getDoctorId());
        log.info(request.getToTimeAsLocalTime());

    }
}
