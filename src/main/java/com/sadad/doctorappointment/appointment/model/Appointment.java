package com.sadad.doctorappointment.appointment.model;

import com.sadad.doctorappointment.appointment.constants.AppointmentStatus;
import com.sadad.doctorappointment.base.model.Audit;
import com.sadad.doctorappointment.doctor.model.Doctor;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"doctor_id", "dateTime", "startTime", "endTime"})})
public class Appointment extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateTime;

    private String startTime;
    private String endTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private String patientName;
    private String patientPhoneNumber;
}
