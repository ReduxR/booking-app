package com.reduxr.service.impl;

import com.reduxr.dto.UserRegistrationRequestDto;
import com.reduxr.dto.UserRegistrationResponseDto;
import com.reduxr.exception.RegistrationException;
import com.reduxr.mapper.UserMapper;
import com.reduxr.model.Role;
import com.reduxr.model.RoleName;
import com.reduxr.model.User;
import com.reduxr.repository.RoleRepository;
import com.reduxr.repository.UserRepository;
import com.reduxr.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(String.format(
                    "User with email: %s already exists", requestDto.getEmail()));
        }

        Role role = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new IllegalStateException(
                        "default " + RoleName.USER + " role is missing"));

        User registeringUser = userMapper.toModel(requestDto);
        registeringUser.setRoles(Set.of(role));
        registeringUser.setPassword(passwordEncoder.encode(registeringUser.getPassword()));

        User savedUser = userRepository.save(registeringUser);

        return userMapper.toDto(savedUser);
    }
}
