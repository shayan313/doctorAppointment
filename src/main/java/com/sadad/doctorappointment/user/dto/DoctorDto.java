package com.sadad.doctorappointment.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.sadad.doctorappointment.user.model.Doctor}
 */
@Data
public class DoctorDto implements Serializable {
   private Long id;
   private String name;
   private String email;
   private String phoneNumber;
   private String specialization;
   private String fromWorkTime;
   private String toWorkTime;
}