package com.kravchi88.tickets.user.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @NotBlank(message = "login is required")
        @Size(min = 3, max = 255, message = "login length must be from 3 to 255")
        @Pattern(regexp = "^(?=.*[A-Za-z])[A-Za-z0-9_]+$",
                 message = "login must contain only latin letters, digits and underscores. Minimum one letter")
        String login,

        @NotBlank(message = "password is required")
        @Size(min = 8, max = 255, message = "password length must be from 8 to 255")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]+$",
                 message = "password must contain only latin letters and digits. Minimum one letter and one digit")
        String password,

        @NotBlank(message = "full name is required")
        @Size(max = 255, message = "full name length must be up to 255")
        @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$",
                 message = "full name must contain only latin letters and spaces. Only one space in a row and between letters")
        String fullName
) {}
