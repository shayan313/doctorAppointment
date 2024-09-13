package com.sadad.doctorappointment.appointment.service.impl;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.appointment.model.Appointment;
import com.sadad.doctorappointment.appointment.projection.AppointmentDoctorInfo;
import com.sadad.doctorappointment.appointment.projection.AppointmentInfo;
import com.sadad.doctorappointment.appointment.repository.AppointmentRepository;
import com.sadad.doctorappointment.appointment.service.IAppointmentService;
import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.user.model.Doctor;
import com.sadad.doctorappointment.user.service.IDoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
        return repository.findByDoctor_IdAndDateTime(doctorId , localDate , status);
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
