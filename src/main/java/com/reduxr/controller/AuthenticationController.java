package com.reduxr.controller;

import com.reduxr.dto.UserLoginRequestDto;
import com.reduxr.dto.UserLoginResponseDto;
import com.reduxr.dto.UserRegistrationRequestDto;
import com.reduxr.dto.UserRegistrationResponseDto;
import com.reduxr.security.jwt.JwtUtil;
import com.reduxr.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("booking/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestDto.email(), requestDto.password()));
        return new UserLoginResponseDto(jwtUtil.generateToken(requestDto.email()));
    }

    @PostMapping("/register")
    public UserRegistrationResponseDto register(
            @Valid @RequestBody UserRegistrationRequestDto requestDto) {
        return userService.register(requestDto);
    }
}
