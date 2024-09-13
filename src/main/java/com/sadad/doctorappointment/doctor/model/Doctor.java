package com.sadad.doctorappointment.doctor.model;


import com.sadad.doctorappointment.base.model.Audit;
import com.sadad.doctorappointment.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table
public class Doctor extends Audit {

    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @org.hibernate.annotations.Parameter(name = "property", value = "user"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    private String specialization;
    private String startWorkTime ;
    private String endWorkTime ;

}
