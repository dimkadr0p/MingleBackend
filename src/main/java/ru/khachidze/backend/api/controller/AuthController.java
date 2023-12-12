package ru.khachidze.backend.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.khachidze.backend.api.dto.*;
import ru.khachidze.backend.api.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponseDto> createAuthToken(@Valid @RequestBody JwtRequestDto authRequest) {
       return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> createNewUser(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(HttpServletRequest request,
                                                    @RequestParam("email")
                                                    @NotBlank(message = "The email must be filled in")
                                                    @Email(message = "Invalid email format") String userEmail
                                            ) {
        return authService.resetPassword(request, userEmail);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<?> resetPassword(@RequestBody DeleteUserDto user) {
        return authService.deleteRegistrationUser(user);
    }
}
