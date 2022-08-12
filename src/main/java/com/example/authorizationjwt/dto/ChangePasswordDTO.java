package com.example.authorizationjwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordDTO {

    @NotBlank(message = "Password should not be empty")
    @Schema(description = "Старый пароль", example = "user")
    String oldPassword;

    @NotBlank(message = "Password should not be empty")
    @Schema(description = "Новый пароль", example = "newUser")
    String newPassword;
}
