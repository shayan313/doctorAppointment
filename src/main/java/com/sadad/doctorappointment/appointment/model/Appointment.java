package com.sadad.doctorappointment.appointment.model;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.base.model.Audit;
import com.sadad.doctorappointment.user.model.Doctor;
import com.sadad.doctorappointment.user.model.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table
public class Appointment extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private UserInfo patient;

    // Constructors, getters, setters
}
