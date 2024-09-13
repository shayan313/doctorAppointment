package com.sadad.doctorappointment.user.repository;

import com.sadad.doctorappointment.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
}

