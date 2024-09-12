package com.sadad.doctorappointment.user.web.admin;

import com.sadad.doctorappointment.user.dto.DoctorDto;
import com.sadad.doctorappointment.user.service.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/doctor")
@RequiredArgsConstructor
public class AdminDoctorController {

    private final IDoctorService iDoctorService;

    @PostMapping("/saveDoctor")
    public DoctorDto saveDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        return iDoctorService.saveOrUpdate(doctorDto);
    }
}
