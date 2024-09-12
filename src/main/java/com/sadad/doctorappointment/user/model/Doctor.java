package com.sadad.doctorappointment.user.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table
public class Doctor extends Person {

    private String specialization;
    private String fromWorkTime ;
    private String toWorkTime ;

}
