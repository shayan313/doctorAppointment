/*
package com.sadad.doctorappointment.user.service.impl;

import com.sadad.doctorappointment.user.model.User;
import com.sadad.doctorappointment.user.repository.UserRepository;
import com.sadad.doctorappointment.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public User findById(Long id) throws AccessDeniedException {
		return userRepository.findById(id).orElse(null);
	}

	public List<User> findAll() throws AccessDeniedException {
		return userRepository.findAll();
	}

	public void changePassword(String oldPassword, String newPassword) {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			log.debug("Re-authenticating user '" + username + "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			log.debug("No authentication manager set. can't change Password!");

			return;
		}

		log.debug("Changing password for user '" + username + "'");

		User user = (User) userDetailsService.loadUserByUsername(username);

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

	}
}
*/
