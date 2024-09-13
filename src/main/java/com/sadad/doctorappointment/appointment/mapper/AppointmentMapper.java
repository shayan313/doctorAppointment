package com.sadad.doctorappointment.appointment.mapper;

import com.sadad.doctorappointment.appointment.dto.AppointmentDto;
import com.sadad.doctorappointment.appointment.model.Appointment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {
    Appointment toEntity(AppointmentDto appointmentDto1);

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.user.firstName", target = "doctorFirstName")
    @Mapping(source = "doctor.user.lastName", target = "doctorLastName")
    @Mapping(source = "doctor.specialization", target = "doctorSpecialization")
    AppointmentDto toDto(Appointment appointment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Appointment partialUpdate(AppointmentDto appointmentDto1, @MappingTarget Appointment appointment);
}