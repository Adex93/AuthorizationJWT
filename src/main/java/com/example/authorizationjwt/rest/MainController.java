package com.example.authorizationjwt.rest;

import com.example.authorizationjwt.entity.User;
import com.example.authorizationjwt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController(value = "/clients")
@Api(tags = "Clients")
@Validated
@RequestMapping("/api/")
public class MainController {

    final
    UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/student")
    @PreAuthorize(value = "hasAuthority('root:student')")
    @Tag(name = "The Test API")
    @Operation(summary = "Test endpoint for students and admins")
    public ResponseEntity<?> sayHelloStudent() {

        log.info("Вызвана функция sayHelloStudent класса MainController");
        User user = getUser().getBody();
        assert user != null;
        String response = "Добро пожаловать, студент " + user.getFirstName() + " " + user.getLastName() + "!";
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/teacher")
    @PreAuthorize(value = "hasAuthority('root:teacher')")
    @Tag(name = "The Test API")
    @Operation(summary = "Test endpoint for teachers and admins")
    public ResponseEntity<?> sayHelloTeacher() {

        log.info("Вызвана функция sayHelloTeacher класса MainController");
        User user = getUser().getBody();
        String response = "Добро пожаловать, преподаватель " + user.getFirstName() + " " + user.getLastName() + "!";
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/admin")
    @PreAuthorize(value = "hasAuthority('root:admin')")
    @Tag(name = "The Test API")
    @Operation(summary = "Test endpoint for admins")
    public ResponseEntity<?> sayHelloAdmin() {

        log.info("Вызвана функция sayHelloAdmin класса MainController");
        User user = getUser().getBody();
        assert user != null;
        String response = "Добро пожаловать, администратор " + user.getFirstName() + " " + user.getLastName() + "!";
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/user_info")
    @Tag(name = "The Test API")
    @Operation(summary = "Get a registered user")
    public ResponseEntity<User> getUser() {

        log.info("Вызвана функция getUser класса MainController");
        User user = userService.getUserByToken();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/users_all")
    @PreAuthorize(value = "hasAuthority('root:admin')")
    @Tag(name = "The Test API")
    @Operation(summary = "Get all users for admins")
    public ResponseEntity<List<User>> getAllUsers() {

        log.info("Вызвана функция getAllUsers класса MainController");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
}
