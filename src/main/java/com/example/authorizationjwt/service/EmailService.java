package com.example.authorizationjwt.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Data
public class EmailService {

    @Value("${spring.mail.username}")
    String smtp;

    @Value("${auth.link.recoverPassword}")
    String recoverLink;

    public final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleEmail(String toAddress, String subject, String message) {
        log.info("Вызвана функция sendSimpleEmail класса EmailService для осуществления отправки письма без вложений");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(smtp);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    public void sendNewPassword(String newPassword, String email) {
        log.info("Вызвана функция sendNewPassword класса EmailService для отправки письма на адрес: " + email);
        String message = "Ваш новый пароль:" + newPassword + "\n"
                + "Сменить указанный пароль Вы можете перейдя по указанной ссылке:\n"
                + recoverLink;

        sendSimpleEmail(email, "Восстановление пароля", message);
    }

}
