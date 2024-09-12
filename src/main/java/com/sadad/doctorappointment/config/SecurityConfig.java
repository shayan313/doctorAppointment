package com.sadad.doctorappointment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class SecurityConfig {

    @Autowired
   private OTPAuthenticationProvider authenticationProvider ;
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->{
                    authorizeRequests.antMatchers("admin/**").hasAnyRole("ROLE_ADMIN");
                    authorizeRequests.antMatchers("user/**").hasAnyRole("ROLE_USER");
                    authorizeRequests.anyRequest().permitAll();
                        }

        ).authenticationProvider(authenticationProvider)
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable();
        return http.build();
    }

}
