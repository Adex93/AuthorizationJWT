package com.example.authorizationjwt.rest;

import com.example.authorizationjwt.dto.AuthenticationRequestDTO;
import com.example.authorizationjwt.dto.ChangePasswordDTO;
import com.example.authorizationjwt.entity.User;
import com.example.authorizationjwt.exceptions.OldPasswordException;
import com.example.authorizationjwt.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@Validated
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    final
    AuthService authService;

    public AuthenticationRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logout")
    @Tag(name = "The Authentication API")
    @Operation(summary = "Logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        log.info("Вызвана функция logout класса AuthenticationRestController");
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/reg")
    @Tag(name = "The Authentication API")
    @Operation(summary = "Create new User")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            log.info("Вызвана функция createUser класса AuthenticationRestController со следующим email пользователя: " + user.getEmail());
            user.setEmail(user.getEmail().toLowerCase());
            return ResponseEntity.ok(authService.createUser(user));
        } catch (DataIntegrityViolationException e) {
            log.error("Пользователь с таким email уже существует");
            return new ResponseEntity<>("Пользователь с таким email уже существует", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/login")
    @Tag(name = "The Authentication API")
    @Operation(summary = "Login(authentication) for Users")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {

        log.info("Вызвана функция authenticate класса AuthenticationRestController со следующим email пользователя: " + request.getEmail());
        try {
            request.setEmail(request.getEmail().toLowerCase());
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (AuthenticationException e) {
            log.error("Invalid email/password combination");
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/change_pass")
    @Tag(name = "The Authentication API")
    @Operation(summary = "Change password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO request) {

        log.info("Вызвана функция changePassword класса AuthenticationRestController");
        try {
            authService.changePassword(request);
            return ResponseEntity.ok("Password changed");
        } catch (OldPasswordException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @PostMapping("/recover_pass")
    @Tag(name = "The Authentication API")
    @Operation(summary = "Recover password (send on email)")
    public ResponseEntity<?> recoverPassword(@RequestBody String email) {

        log.info("Вызвана функция recoverPassword класса AuthenticationRestController  со следующим телом: " + email);
        authService.recoverPassword(email.toLowerCase());

        return ResponseEntity.ok("Password recovered");
    }

}

