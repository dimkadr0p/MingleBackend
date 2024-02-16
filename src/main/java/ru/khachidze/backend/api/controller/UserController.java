package ru.khachidze.backend.api.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.khachidze.backend.api.exception.UserNotFoundException;
import ru.khachidze.backend.store.entity.UserEntity;
import ru.khachidze.backend.store.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final String PHOTO_PATH = "D:\\MingleProject\\backend\\src\\main\\resources\\db\\changelog\\photos";
    private static final String PHOTO_URL = "http://localhost:8080/api/photos/";

    @GetMapping("/user/info")
    public UserEntity userData(Principal principal) {
        return userRepository.findByName(principal.getName()).orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    @GetMapping("/photos/{photoPath}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String photoPath) {
        try {
            File photoFile = new File(PHOTO_PATH, photoPath);
            byte[] photoBytes = Files.readAllBytes(photoFile.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(photoBytes.length);
            return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/user/upload")
    public ResponseEntity<String> uploadPhoto(Principal principal, @RequestParam("file") MultipartFile file) {
        try {
            UserEntity user = userRepository.findByName(principal.getName()).orElseThrow(() -> new NotFoundException("User not found"));

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String filename = UUID.randomUUID().toString() + ".jpg";

            File destinationFile = new File(PHOTO_PATH, filename);
            file.transferTo(destinationFile);

            user.setPhoto(PHOTO_URL + filename);
            userRepository.save(user);

            return ResponseEntity.ok("Photo uploaded successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload photo");
        }
    }

}
