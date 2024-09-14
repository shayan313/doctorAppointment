package com.sadad.doctorappointment.user.service.impl;

import com.sadad.doctorappointment.user.dto.UserDto;
import com.sadad.doctorappointment.user.mapper.UserMapper;
import com.sadad.doctorappointment.user.model.Authority;
import com.sadad.doctorappointment.user.model.User;
import com.sadad.doctorappointment.user.model.UserRoleName;
import com.sadad.doctorappointment.user.repository.UserRepository;
import com.sadad.doctorappointment.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

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

    @Override
	@Transactional
    public UserDto saveOrUpdate(UserDto userDto) {
		var existingUser = userRepository.findByIdWithLock(userDto.getId());
		if (existingUser.isPresent()) {
			return updateUser(existingUser.get(), userDto);
		}else {
			return saveUser(userDto);
		}
    }

    @Transactional
    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        var authority= new Authority();
        authority.setName(UserRoleName.ROLE_USER);
        user.setAuthorities(List.of(authority));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("updateUser Version={} ", savedUser.getVersion());
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto updateUser(User existingUser, UserDto userDto) {
		userMapper.partialUpdate(userDto, existingUser);
        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User updatedUser = userRepository.save(existingUser);
        log.info("updateUser Version={} ", updatedUser.getVersion());
        return userMapper.toDto(updatedUser);
    }

}