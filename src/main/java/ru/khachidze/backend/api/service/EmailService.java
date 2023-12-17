package ru.khachidze.backend.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMessageToEmail(String to, String subject, String text) {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom("hachidze.d@yandex.ru", "Mingle");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            emailSender.send(mimeMessage);
        } catch (Exception ex) {
            log.error("In sendSimpleMessage: Произошла ошибка отправки почты пользователю " +
                    "{} предмет {} текст {}. Ошибка: {}", to, subject, text, ex.getMessage());
        }

    }
}
