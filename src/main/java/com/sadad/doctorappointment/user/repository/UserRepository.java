package com.sadad.doctorappointment.user.repository;

import com.sadad.doctorappointment.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    //@Query("select (count(u) > 0) from UserInfo u where upper(u.email) = upper(:email)")
    boolean existsByEmailIgnoreCase( String email);

    Optional<UserInfo> findByPhoneNumber(String username);
}