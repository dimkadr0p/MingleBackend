package ru.khachidze.backend.api.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.khachidze.backend.api.dto.PasswordResetDto;
import ru.khachidze.backend.api.service.UserService;
import ru.khachidze.backend.store.entity.UserEntity;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
public class ResetPasswordPageController {

    @Autowired
    private UserService userService;

    @GetMapping("/changePassword")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {

        log.info("In showResetPasswordPage: Попал в контроллер токен {}", token);

        Optional<UserEntity> user = userService.getUserByPasswordResetToken(token);

        if (user.isPresent()) {
            model.addAttribute("token", token);
            model.addAttribute("passwordResetDto", new PasswordResetDto());
            log.info("Возращена страничка для восстановление пароля");
            return "resetPasswordFormPage";
        } else {
            log.info("In showResetPasswordPage: Возвращена страница о том, что токен больше не валиден {}", token);
            return "invalidTokenPage";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("token") String token, @Valid @ModelAttribute PasswordResetDto passwordResetDto) {
        if(userService.updateUserPassword(token, passwordResetDto.getPassword())) {
            userService.deleteTokenResetPassword(token);
            return "okPage";
        } else {
            return "failedPage";
        }

    }

}
