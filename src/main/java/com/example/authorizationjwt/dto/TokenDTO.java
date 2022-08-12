package com.example.authorizationjwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenDTO {

    @Schema(description = "Электронная почта", example = "dmitriev_alexandr93@mail.ru")
    String email;

    @Schema(description = "JSON Web Token")
    String token;
}
