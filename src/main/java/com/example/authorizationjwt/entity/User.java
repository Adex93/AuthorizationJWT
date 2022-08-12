package com.example.authorizationjwt.entity;

import com.example.authorizationjwt.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "role")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Schema(description = "Роль (STUDENT, TEACHER или ADMIN)", example = "STUDENT")
    Role role;

    @Column(name = "password")
    @NotBlank(message = "Password should not be empty")
    @Schema(description = "Пароль", example = "user")
    private String password;

    @Column(name = "first_name")
    @Pattern(regexp = "^[A-Za-z]+$")
    @Size(min = 2, max = 30, message = "FirstName should be between 2 and 30 characters")
    @NotBlank(message = "FirstName should not be empty")
    @Schema(description = "Имя", example = "Ivan")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "^[A-Za-z]+$")
    @Size(min = 2, max = 30, message = "lastName should be between 2 and 30 characters")
    @NotBlank(message = "lastName should not be empty")
    @Schema(description = "Фамилия", example = "Ivanov")
    private String lastName;

    @Column(name = "company")
    @Schema(description = "Принадлежность к компании (не обязательное поле)", example = "Neoflex")
    private String company;

    @Column(name = "email")
    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email is uncorrected")
    @Schema(description = "Электронная почта", example = "dmitriev_alexandr93@mail.ru")
    private String email;

    @Column(name = "birth_date")
    @NotNull
    @Past(message = "Birthdate is uncorrected")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата рождения", example = "1993-07-28")
    private LocalDate birthDate;

    @Column(name = "city")
    @NotBlank(message = "City should not be empty")
    @Schema(description = "Город", example = "Voroneg")
    private String city;

    @Column(name = "phone")
    @NotBlank(message = "Phone should not be empty")
    @Schema(description = "Номер телефона", example = "89511231212")
    private String phone;

    @Column(name = "create_at")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDate createAT;
}
