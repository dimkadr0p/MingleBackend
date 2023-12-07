package ru.khachidze.backend.api.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khachidze.backend.api.dto.JwtResponseDto;
import ru.khachidze.backend.api.dto.RegistrationUserDto;
import ru.khachidze.backend.api.dto.UserDto;
import ru.khachidze.backend.api.service.AuthService;
import ru.khachidze.backend.api.service.UserService;
import ru.khachidze.backend.store.entity.UserEntity;

import java.security.Principal;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class MainController {

    private final UserService userService;

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "unsecured data";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "secured data";
    }

    @GetMapping("/info")
    public ResponseEntity<?> userData(Principal principal) {
        return ResponseEntity.ok(principal);
    }

    @GetMapping("/usersAll")
    public List<UserDto> getUsers() {
        log.info("получили всех users");
        return userService.getAllUsers();
    }
}
