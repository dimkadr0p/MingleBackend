package ru.khachidze.backend.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.khachidze.backend.api.exception.UserNotFoundException;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.UserRepository;

import java.security.Principal;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @GetMapping("/user/info")
    public UserEntity userData(Principal principal) {
        return userRepository.findByName(principal.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    @PostMapping("/user/upload")
    public ResponseEntity<String> uploadPhoto(Principal principal, @RequestBody Map<String, String> request) {
        try {
            String base64Data = request.get("photo");
            byte[] photoBytes = Base64.getDecoder().decode(base64Data);

            UserEntity user = userRepository.findByName(principal.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));
            user.setPhoto(photoBytes);
            userRepository.save(user);
            return ResponseEntity.ok("Фотография успешно загружена");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка загрузки фотографии");
        }
    }

}
