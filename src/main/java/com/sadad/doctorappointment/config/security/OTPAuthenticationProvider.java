/*
package com.sadad.doctorappointment.config;

import com.sadad.doctorappointment.user.model.User;
import com.sadad.doctorappointment.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class OTPAuthenticationProvider implements AuthenticationProvider {

    private static final String FIXED_OTP = "123456";

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getName();
        String otp = authentication.getCredentials().toString();

        // اعتبارسنجی شماره تلفن کاربر
        Optional<User> patient = userRepository.findByPhoneNumber(phoneNumber);
        if (patient.isEmpty()) {
            throw new BadCredentialsException("authentication.invalid.phone.number");
        }

        // اعتبارسنجی OTP ثابت
        if (!FIXED_OTP.equals(otp)) {
            throw new BadCredentialsException("authentication.invalid.otp");
        }

        // اگر اعتبارسنجی موفق بود
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(phoneNumber, otp, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
*/
