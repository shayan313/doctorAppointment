package com.sadad.doctorappointment.user.service.impl;

import com.sadad.doctorappointment.base.exception.ApplicationException;
import com.sadad.doctorappointment.user.dto.UserDto;
import com.sadad.doctorappointment.user.model.UserInfo;
import com.sadad.doctorappointment.user.repository.UserRepository;
import com.sadad.doctorappointment.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;

    @Override
    public void registerUser(UserDto userDto) {

        if (repository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new ApplicationException("registerUser.existsByPhoneNumber.exception");
        }
        if (repository.existsByEmailIgnoreCase(userDto.getEmail())) {
            throw new ApplicationException("registerUser.existsByEmail.exception");
        }

        var user = new UserInfo();
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        // بدون ذخیره رمز عبور

        repository.save(user);


    }
}
