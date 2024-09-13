package com.sadad.doctorappointment.doctor.mapper;

import com.sadad.doctorappointment.doctor.dto.DoctorDto;
import com.sadad.doctorappointment.doctor.model.Doctor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DoctorMapper {

    @Mapping(source = "userId", target = "user.id")
    Doctor toEntity(DoctorDto doctorDto);

    @Mapping(target = "userId", source = "user.id")
    DoctorDto toDto(Doctor doctor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Doctor partialUpdate(DoctorDto doctorDto, @MappingTarget Doctor doctor);
}