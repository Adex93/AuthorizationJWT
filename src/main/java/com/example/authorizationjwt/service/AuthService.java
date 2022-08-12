package com.example.authorizationjwt.service;

import com.example.authorizationjwt.dto.AuthenticationRequestDTO;
import com.example.authorizationjwt.dto.ChangePasswordDTO;
import com.example.authorizationjwt.dto.TokenDTO;
import com.example.authorizationjwt.entity.User;
import com.example.authorizationjwt.exceptions.OldPasswordException;
import com.example.authorizationjwt.repository.UserRepository;
import com.example.authorizationjwt.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Slf4j
@Service
public class AuthService {

    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserService userService, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.emailService = emailService;
    }

    public TokenDTO createUser(@RequestBody User user) {

        log.info("Вызвана функция createUser класса AuthService для осуществления регистрации нового пользователя: " + user.getEmail());
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();
        authenticationRequestDTO.setPassword(user.getPassword());
        authenticationRequestDTO.setEmail(user.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateAT(LocalDate.now());

        userRepository.save(user);

        return authenticate(authenticationRequestDTO);
    }

    public TokenDTO authenticate(@RequestBody AuthenticationRequestDTO request) {

        log.info("Вызвана функция authenticate класса AuthService для осуществления авторизации пользователя: " + request.getEmail());
        TokenDTO tokenDTO = new TokenDTO();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
        tokenDTO.setToken(token);
        tokenDTO.setEmail(request.getEmail());
        return tokenDTO;
    }

    public void changePassword(@RequestBody ChangePasswordDTO request) {

        User user = userService.getUserByToken();
        log.info("Вызвана функция changePassword класса AuthService для осуществления смены пароля" + user.getEmail());
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        } else throw new OldPasswordException("Invalid old password", HttpStatus.FORBIDDEN);
    }

    public void recoverPassword(@RequestBody String email) {

        log.info("Вызвана функция recoverPassword класса AuthService для осуществления отправки нового сгенерированного пароля на почту" + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        String newPass = RandomStringUtils.randomAlphanumeric(10);

        user.setPassword(passwordEncoder.encode(newPass));
        emailService.sendNewPassword(newPass, email);
        userRepository.save(user);
    }

}
