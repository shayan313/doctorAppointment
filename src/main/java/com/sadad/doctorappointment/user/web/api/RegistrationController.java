package com.sadad.doctorappointment.user.web.api;

import com.sadad.doctorappointment.user.dto.UserDto;
import com.sadad.doctorappointment.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class RegistrationController {

    private final IUserService iUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        iUserService.registerUser(userDto);
        return ResponseEntity.ok("Registration successful");
    }
}
