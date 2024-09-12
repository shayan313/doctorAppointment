package com.sadad.doctorappointment.appointment.service.impl;

import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.repository.AppointmentRepository;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository repository ;
    @Override
    public Page<Appointment> getAll(Date currentDate, Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void setSlots(SlotsRequest request) {

        log.info(request.getToTimeAsLocalTime());



    }
}
