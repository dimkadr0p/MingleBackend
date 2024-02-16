package ru.khachidze.backend.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.khachidze.backend.api.dto.*;
import ru.khachidze.backend.api.exception.*;
import ru.khachidze.backend.api.util.JwtTokenUtil;
import ru.khachidze.backend.store.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;

    public ResponseEntity<UserDto> createNewUser(RegistrationUserDto registrationUserDto) {

        if (userService.isUserLoginExists(registrationUserDto.getName())) {
            log.info("In createNewUser: Пользователь, " +
                    "предоставивший данные для регистрации login: {} и email: {} " +
                    "-такой логин уже существует", registrationUserDto.getName(), registrationUserDto.getEmail());
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        if (userService.isUserEmailExists(registrationUserDto.getEmail())) {
            log.info("In createNewUser: Пользователь, " +
                    "предоставивший данные для регистрации login: {} и email: {} " +
                    "-такая почта уже существует", registrationUserDto.getName(), registrationUserDto.getEmail());
            throw new EmailAlreadyExistsException("Specified mail already exists");
        }

        UserEntity user = userService.createNewUser(registrationUserDto);

        log.info("In createNewUser: Пользователь, " +
                "предоставивший данные для регистрации login: {} и email: {} " +
                "успешно зарегистрирован", registrationUserDto.getName(), registrationUserDto.getEmail());

        return ResponseEntity.ok(new UserDto(user.getId(), user.getName(), user.getEmail()));
    }

    public ResponseEntity<JwtResponseDto> createAuthToken(JwtRequestDto authRequest) {
        try {
            log.info("In createNewUser: Попытка входа пользователя " +
                    "login: {}", authRequest.getName());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            log.info("In createNewUser: Пользователь введенный данные " +
                    "login: {} были неверными", authRequest.getName());
            throw new CustomAuthenticationException("Incorrect login or password");
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getName());
        String token = jwtTokenUtil.generateToken(userDetails);

        log.info("In createNewUser: Пользователю " +
                "login: {} и password: {} успешно выдан токен", authRequest.getName(), authRequest.getPassword());

        return ResponseEntity.ok(new JwtResponseDto(token));
    }


    public ResponseEntity<?> resetPassword(HttpServletRequest request, String email) {

        UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email not Found"));

        String token = UUID.randomUUID().toString();

        userService.createPasswordResetTokenForUser(user, token);

        String toSendMessage = constructResetTokenEmail(getAppUrl(request), token);

        emailService.sendMessageToEmail(user.getEmail(), "Восстановление пароля", toSendMessage);

        log.info("Сообщение доставлено на почту {}", user.getEmail());

        return ResponseEntity.ok(new GenericResponseDto("token has been sent to the email"));
    }

    public ResponseEntity<?> deleteRegistrationUser(DeleteUserDto user) {
        UserEntity userEntity = userService.findByName(user.getName())
                .orElseThrow(() -> new UserNotFoundException("User not Found"));

        userService.deleteUser(userEntity);
        log.info("user: {} удален", userEntity.getName());
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponseDto("User is deleted"));
    }

    private String constructResetTokenEmail(String contextPath, String token) {
        String url = contextPath + "/changePassword?token=" + token;
        return "Восстановите пароль по этой ссылке:" + " \r\n" + url;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
