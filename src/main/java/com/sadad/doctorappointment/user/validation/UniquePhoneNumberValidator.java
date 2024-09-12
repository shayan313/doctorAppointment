package com.sadad.doctorappointment.user.validation;

import com.sadad.doctorappointment.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }

}
