package com.sadad.doctorappointment.user.model;


import com.sadad.doctorappointment.appointment.model.Appointment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table
public class Doctor extends Person {

    private String specialization;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Appointment> appointments;
}
