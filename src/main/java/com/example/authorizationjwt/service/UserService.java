package com.example.authorizationjwt.service;

import com.example.authorizationjwt.entity.User;

import com.example.authorizationjwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    final
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByToken() {

        log.info("Вызвана функция getUserByToken класса UserService для осуществления поиска пользователя по токену");
        Optional<User> optionalUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else throw new RuntimeException("User not found");
    }

    public User getUserByEmail(String email) {

        log.info("Вызвана функция getUserByToken класса UserService для осуществления поиска пользователя по email" + email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else throw new RuntimeException("User not found");
    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        log.info("Вызвана функция getAllUsers класса MainController для получения списка всех пользователей");
        Iterator<User> iterator = userRepository.findAll().iterator();
        iterator.forEachRemaining(list::add);

        return list;
    }

}
