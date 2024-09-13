package com.sadad.doctorappointment.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .antMatchers( "/**","/actuator/**", "/api-docs/**", "/instances/**" , "/h2-console" , "/swagger-ui/**").permitAll()
                        .antMatchers("/api/doctor/**").hasRole("DOCTOR")  // دسترسی فقط برای پزشکان
                        .antMatchers("/api/user/**").hasRole("USER")  // دسترسی فقط برای بیماران
                        .antMatchers("/open/**").permitAll()  // دسترسی عمومی بدون نیاز به احراز هویت
                        .anyRequest().authenticated()

                        .and()
                )

                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(LogoutConfigurer::permitAll
                )

                .headers()
                .frameOptions()
                .disable()
                .and()
                .csrf().disable();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails doctor = User.builder()
                .username("doctor")
                .password(passwordEncoder.encode("123"))
                .roles("DOCTOR")
                .build();

        UserDetails patient = User.builder()
                .username("user")
                .password(passwordEncoder.encode("123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(doctor, patient);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
