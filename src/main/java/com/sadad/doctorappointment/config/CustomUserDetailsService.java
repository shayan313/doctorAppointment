/*
package com.sadad.doctorappointment.config;

import com.sadad.doctorappointment.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByPhoneNumber(username)
            .orElseThrow(() -> new UsernameNotFoundException("UserInfo not found"));
        return new UserInfo(user.getPhoneNumber(),null, getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserInfo user) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
*/
