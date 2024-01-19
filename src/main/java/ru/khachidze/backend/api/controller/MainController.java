package ru.khachidze.backend.api.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khachidze.backend.api.dto.UserDto;
import ru.khachidze.backend.api.service.UserService;

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

}
