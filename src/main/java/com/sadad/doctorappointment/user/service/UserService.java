package com.sadad.doctorappointment.user.service;

import com.sadad.doctorappointment.user.model.User;

import java.util.List;

public interface UserService {
	User findById(Long id);

	User findByUsername(String username);

	List<User> findAll();

	void changePassword(String oldPassword, String newPassword);
}
