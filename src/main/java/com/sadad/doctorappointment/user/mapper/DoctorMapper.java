package com.sadad.doctorappointment.user.mapper;

import com.sadad.doctorappointment.user.dto.DoctorDto;
import com.sadad.doctorappointment.user.model.Doctor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DoctorMapper {
    Doctor toEntity(DoctorDto doctorDto);

    DoctorDto toDto(Doctor doctor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Doctor partialUpdate(DoctorDto doctorDto, @MappingTarget Doctor doctor);
}