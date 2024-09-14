package com.sadad.doctorappointment.appointment.service.impl;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.appointment.dto.AppointmentRequest;
import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.projection.AppointmentDoctorInfo;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;
import com.sadad.doctorappointment.appointment.repository.AppointmentRepository;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.doctor.model.Doctor;
import com.sadad.doctorappointment.user.service.IDoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository repository;
    private final IDoctorService iDoctorService;

    @Override
    public List<AppointmentInfo> findByDateTimeAndDoctor_Id(LocalDate localDate, Long doctorId) {
        return repository.findByDateTimeAndDoctor_Id(localDate, doctorId);
    }

    @Override
    public List<AppointmentDoctorInfo> findAllByDoctor_Id(Long doctorId, LocalDate localDate, AppointmentStatus status) {
        return repository.findByDoctor_IdAndDateTime(doctorId, localDate, status);
    }

    @Override
    @Transactional
    public List<Appointment> setSlots(SlotsRequest request) {

        if (request.getFromTimeAsLocalTime().isAfter(request.getToTimeAsLocalTime())) {
            throw new ApplicationException("appointment.FromTime.isAfter.ToTime");
        }

        var doctor = iDoctorService.findById(request.getDoctorId());
        return createAppointments(request.getFromTimeAsLocalTime(), request.getToTimeAsLocalTime(), doctor, request.getCurrentDateAsLocalDate());


    }

    @Override
    @Transactional
    public void deleteAppointment(Long appointmentId) {
        var entity = repository.findByIdWithLock(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("appointment.not.found.exception"));

        // TODO: 9/14/2024  برسی دسترسی کاربر به  ملافات های خودش فقط 
        /*
           if (!entity.getDoctor().getId().equals(doctorId)) {
            throw new ApplicationException();
        }*/

        if (!AppointmentStatus.OPEN.equals(entity.getStatus())) {
            throw new ApplicationException("appointment.status.isNot.open.exception");
        }

        repository.delete(entity);

    }

    @Override
    public Optional<Appointment> findById(Long appointmentId) {
        return repository.findById(appointmentId);
    }

    @Override
    public List<AppointmentInfo> availableAppointment(Long doctorId, LocalDate localDate) {
        return repository.findByDoctor_IdAndDateTimeAndStatus(doctorId, localDate, AppointmentStatus.OPEN);
    }

    @Override
    @Transactional
    public Appointment takenAppointment(AppointmentRequest request) {

        var entity = repository.findByIdWithLock(request.getAppointmentId())
                .orElseThrow(() -> new EntityNotFoundException("appointment.not.found.exception"));

        if (!AppointmentStatus.OPEN.equals(entity.getStatus())) {
            throw new ApplicationException("appointment.status.isNot.open.exception", HttpStatus.NOT_ACCEPTABLE);
        }

        entity.setPatientName(request.getName());
        entity.setPatientPhoneNumber(request.getPhoneNumber());
        entity.setStatus(AppointmentStatus.TAKEN);

        repository.save(entity);
        return entity;
    }

    @Override
    public List<AppointmentInfo> getUserAppointments(String phoneNumber, Long doctorId, LocalDate localDate) {
        return repository.getUserAppointments(phoneNumber, doctorId, localDate);
    }


    @Transactional
    public List<Appointment> createAppointments(LocalTime startTime, LocalTime endTime, Doctor doctor, LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();
        LocalTime currentStartTime = startTime;
        LocalTime startWorkTime = LocalTime.parse(doctor.getStartWorkTime());
        LocalTime endWorkTime = LocalTime.parse(doctor.getEndWorkTime());

        while (currentStartTime.isBefore(endTime)) {
            LocalTime currentEndTime = currentStartTime.plusMinutes(30);
            if (currentEndTime.isAfter(endTime)) {
                break;
            }
            if (!currentStartTime.isBefore(startWorkTime) && !endWorkTime.isBefore(currentEndTime)) {
                Appointment appointment = Appointment.builder()
                        .dateTime(date)
                        .startTime(currentStartTime.toString())
                        .endTime(currentEndTime.toString())
                        .status(AppointmentStatus.OPEN)
                        .doctor(doctor)
                        .build();
                appointments.add(repository.save(appointment));
            }
            currentStartTime = currentEndTime;

        }
        return appointments;
    }

    /*private List<LocalTime[]> createTimeSlots(LocalTime startTime, LocalTime endTime) {
        List<LocalTime[]> timeSlots = new ArrayList<>();
        while (startTime.isBefore(endTime)) {
            LocalTime slotEnd = startTime.plusMinutes(30);
            if (slotEnd.isAfter(endTime)) {
                break;
            }
            timeSlots.add(new LocalTime[]{startTime, slotEnd});
            startTime = slotEnd;
        }
        return timeSlots;
    }*/
}
