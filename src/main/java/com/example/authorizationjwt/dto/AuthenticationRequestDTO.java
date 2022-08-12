package com.example.authorizationjwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequestDTO {

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email is uncorrected")
    @Schema(description = "Электронная почта", example = "dmitriev_alexandr93@mail.ru")
    private String email;

    @NotBlank(message = "Password should not be empty")
    @Schema(description = "Пароль", example = "user")
    private String password;
}