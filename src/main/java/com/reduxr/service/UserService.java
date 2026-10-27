package com.reduxr.service;

import com.reduxr.dto.UserRegistrationRequestDto;
import com.reduxr.dto.UserRegistrationResponseDto;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto);
}
