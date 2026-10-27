package com.reduxr.dto;

public record UserRegistrationResponseDto(
        Long id,
        String email,
        String firstName,
        String lastName) {
}
